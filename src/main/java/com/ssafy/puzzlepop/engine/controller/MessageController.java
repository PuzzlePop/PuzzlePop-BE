package com.ssafy.puzzlepop.engine.controller;

import com.ssafy.puzzlepop.engine.domain.GameType;
import com.ssafy.puzzlepop.engine.InGameMessage;
import com.ssafy.puzzlepop.engine.domain.Game;
import com.ssafy.puzzlepop.engine.domain.User;
import com.ssafy.puzzlepop.engine.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@EnableScheduling
public class MessageController {

    @Autowired
    private GameService gameService;
    private final SimpMessageSendingOperations sendingOperations;
    private final int BATTLE_TIMER = 5;

    @MessageMapping("/game/message")
    public void enter(InGameMessage message) {
        if (InGameMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }

        if (message.getType().equals(InGameMessage.MessageType.ENTER)) {
            System.out.println(gameService.findById(message.getRoomId()).getGameName() + "에 " + message.getSender() + "님이 입장하셨습니다.");
            if (gameService.findById(message.getRoomId()).enterPlayer(new User(message.getSender()))) {
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(),message);
            } else {
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(),"방 가득참");
            }
        } else {
            if (message.getMessage().equals("gameStart")) {
                System.out.println("game start");
                Game game = gameService.startGame(message.getRoomId());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            } else {
                System.out.println("명령어 : " + message.getTargets());
                Game game = gameService.playGame(message.getRoomId(), message.getMessage(), message.getTargets());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            }
        }


    }

    //서버 타이머  제공
    @Scheduled(fixedRate = 1000)
    public void sendServerTime() {
        List<Game> allRoom = gameService.findAllRoom();
        for (int i = 0; i < allRoom.size(); i++) {
            if (allRoom.get(i).isStarted()) {
                long time = allRoom.get(i).getTime();
                if (allRoom.get(i).getGameType() == GameType.BATTLE) {
                    time = BATTLE_TIMER-time;
                }
                sendingOperations.convertAndSend("/topic/game/room/" + allRoom.get(i).getGameId(), time);
                System.out.println(allRoom.get(i).getGameName() + "에 " + allRoom.get(i).getTime() + "초 라고 보냈음");
            }
        }
    }

}

