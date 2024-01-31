package com.ssafy.puzzlepop.engine.controller;

import com.ssafy.puzzlepop.engine.InGameMessage;
import com.ssafy.puzzlepop.engine.SocketError;
import com.ssafy.puzzlepop.engine.domain.Game;
import com.ssafy.puzzlepop.engine.domain.GameType;
import com.ssafy.puzzlepop.engine.domain.ResponseMessage;
import com.ssafy.puzzlepop.engine.domain.User;
import com.ssafy.puzzlepop.engine.service.GameService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@EnableScheduling
public class MessageController {

    @Autowired
    private GameService gameService;
    private final SimpMessageSendingOperations sendingOperations;
    private final int BATTLE_TIMER = 300;
    private String sessionId;
    private Map<String, String> sessionToGame;

    @PostConstruct
    public void init() {
        sessionToGame = new HashMap<>();
    }

    //세션 아이디 설정
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        System.out.println("MessageController.handleWebSocketConnectListener");
        System.out.println(event.getMessage().getHeaders().get("simpSessionId"));
        sessionId = (String) event.getMessage().getHeaders().get("simpSessionId");
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        System.out.println("MessageController.handleDisconnectEvent");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String gameId = sessionToGame.get(sessionId);
        Game game = gameService.findById(gameId);
        System.out.println(game.getSessionToUser().get(sessionId).getId() + " 님이 퇴장하십니다.");
        game.exitPlayer(sessionId);
        sessionToGame.remove(sessionId);

        if (game.isEmpty()) {
            System.out.println("game.isEmpty()");
            gameService.deleteRoom(gameId);
        }

        sendingOperations.convertAndSend("/topic/game/room/"+gameId, game);
    }



    @MessageMapping("/game/message")
    public void enter(InGameMessage message) {
        if (message.getType().equals(InGameMessage.MessageType.ENTER)) {
            Game game = gameService.findById(message.getRoomId());

            sessionToGame.put(sessionId, message.getRoomId());

            if (game.enterPlayer(new User(message.getSender()), sessionId)) {
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
                System.out.println(gameService.findById(message.getRoomId()).getGameName() + "에 " + message.getSender() + "님이 입장하셨습니다.");
            } else {
                System.out.println("방 입장 실패");
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(),new SocketError("room", "방 가득 참"));
                System.out.println(gameService.findById(message.getRoomId()).getGameName() + "에 " + message.getSender() + "님이 입장하지 못했습니다.");
            }
        } else {
            if (message.getMessage().equals("GAME_START")) {
                System.out.println("GAME_START");
                Game game = gameService.startGame(message.getRoomId());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            } else {
                System.out.println("명령어 : " + message.getMessage());
                System.out.println("게임방 : " + message.getRoomId());
                ResponseMessage res = gameService.playGame(message.getRoomId(), message.getMessage(), message.getTargets());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), res);
            }
        }
    }

    //서버 타이머  제공
    @Scheduled(fixedRate = 1000)
    public void sendServerTime() {
        List<Game> allRoom = gameService.findAllRoom();
        for (int i = allRoom.size()-1; i >= 0 ; i--) {
            if (allRoom.get(i).isStarted()) {
                long time = allRoom.get(i).getTime();
                if (allRoom.get(i).getGameType() == GameType.BATTLE) {
                    time = BATTLE_TIMER-time;
                }
                if (time > 0) {
                    sendingOperations.convertAndSend("/topic/game/room/" + allRoom.get(i).getGameId(), time);
                } else {
                    sendingOperations.convertAndSend("/topic/game/room/" + allRoom.get(i).getGameId(), "너 게임 끝났어! 이 방 폭파됨");
                    gameService.deleteRoom(allRoom.get(i).getGameId());
                }
//                System.out.println(allRoom.get(i).getGameName() + "에 " + allRoom.get(i).getTime() + "초 라고 보냈음");
            }
        }
    }

}

