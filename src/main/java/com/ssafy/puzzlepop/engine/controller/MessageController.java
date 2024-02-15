package com.ssafy.puzzlepop.engine.controller;

import com.ssafy.puzzlepop.engine.InGameMessage;
import com.ssafy.puzzlepop.engine.SocketError;
import com.ssafy.puzzlepop.engine.domain.*;
import com.ssafy.puzzlepop.engine.service.GameService;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.service.ImageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import static com.ssafy.puzzlepop.engine.controller.GameRoomController.encodeImageToBase64;

@Controller
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class MessageController {
    private final GameService gameService;
    private final SimpMessageSendingOperations sendingOperations;
    private final ImageService imageService;
    private final int BATTLE_TIMER = 300;
    private String sessionId;
    private final Queue<User> waitingList = new LinkedList<>();


    //세션 아이디 설정
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        System.out.println("MessageController.handleWebSocketConnectListener");
        System.out.println(event.getMessage().getHeaders().get("simpSessionId"));
        sessionId = (String) event.getMessage().getHeaders().get("simpSessionId");
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) throws InterruptedException {
        System.out.println("MessageController.handleDisconnectEvent");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String gameId = gameService.sessionToGame.get(sessionId);
        Game game = gameService.findById(gameId);
        if (game == null) {
            return;
        }

        if (accessor.getCommand().equals(StompCommand.DISCONNECT)) {
            if (game.isFinished()) {
                System.out.println(game.getSessionToUser().get(sessionId).getId() + " 님이 퇴장하십니다.");
                game.exitPlayer(sessionId);
                gameService.sessionToGame.remove(sessionId);
            } else {
                if (!game.isStarted()) {
                    //잠시 대기
//                    Thread.sleep(5000);
//                    if (game.isEmpty()) {
//                        System.out.println("진짜 나간것같아. 게임 지울게!");
//                        gameService.deleteRoom(gameId);
//                    } else {
//                        System.out.println("새로고침이였어. 다시 연결한다!");
//                        return;
//                    }
                    System.out.println(game.getSessionToUser().get(sessionId).getId() + " 님이 퇴장하십니다.");
                    game.exitPlayer(sessionId);

                    gameService.sessionToGame.remove(sessionId);
                } else {
                    System.out.println("어딜 나가 이자식아");
                    return;
                }
            }
        }

        sendingOperations.convertAndSend("/topic/game/room/"+gameId, game);
    }



    @MessageMapping("/game/message")
    public void enter(InGameMessage message) throws Exception {
        if (message.getType().equals(InGameMessage.MessageType.ENTER)) {
            Game game = gameService.findById(message.getRoomId());

            gameService.sessionToGame.put(sessionId, message.getRoomId());

            if (game.enterPlayer(new User(message.getSender(), message.isMember(), sessionId), sessionId)) {
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
                System.out.println(gameService.findById(message.getRoomId()).getGameName() + "에 " + message.getSender() + " " + message.isMember() + "님이 입장하셨습니다.");
            } else {
                System.out.println("방 입장 실패");
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(),new SocketError("room", "방 가득 참"));
                System.out.println(gameService.findById(message.getRoomId()).getGameName() + "에 " + message.getSender() + "님이 입장하지 못했습니다.");
            }
        } else if (message.getType().equals(InGameMessage.MessageType.CHAT)) {
            ResponseChatMessage responseChatMessage = new ResponseChatMessage();
            responseChatMessage.setChatMessage(message.getMessage());
            responseChatMessage.setUserid(message.getSender());
            Game game = gameService.findById(message.getRoomId());

            if (game.getRedTeam().isIn(message.getSender())) {
                responseChatMessage.setTeamColor("RED");
            } else if (game.getBlueTeam().isIn(message.getSender())){
                responseChatMessage.setTeamColor("BLUE");
            }

            responseChatMessage.setTime(new Date());
            sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), responseChatMessage);
        } else if (message.getType().equals(InGameMessage.MessageType.IMAGE)) {
            Game game = gameService.findById(message.getRoomId());
            sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
        }

        else if (message.getType().equals(InGameMessage.MessageType.QUICK)) {
            User user = new User(message.getSender(), message.isMember(), sessionId);
            ResponseMessage res = new ResponseMessage();
            if (waitingList.contains(user)) {
                res.setMessage("WAITING");
                sendingOperations.convertAndSend("/topic/game/room/quick/"+ message.getSender(), res);
                return;
            }

            waitingList.add(user);


            if (waitingList.size() >= 2) {
                User player1 = waitingList.poll();
                User player2 = waitingList.poll();

                Room room = new Room();
                room.setName(UUID.randomUUID().toString());
                room.setRoomSize(2);
                room.setGameType("BATTLE");
                room.setUserid(player1.getId());
                gameService.sessionToGame.put(sessionId, player1.getId());
                gameService.sessionToGame.put(sessionId, player2.getId());

                Game game = gameService.createRoom(room);
                game.enterPlayer(player1, sessionId);
                game.enterPlayer(player2, sessionId);

                gameService.startGame(game.getGameId());

                res.setMessage("GAME_START");
                res.setTargets(game.getGameId());
                res.setTeam("RED");
                sendingOperations.convertAndSend("/topic/game/room/quick/"+ player1.getId(), res);
                res.setTeam("BLUE");
                sendingOperations.convertAndSend("/topic/game/room/quick/"+ player2.getId(), res);
                
                sendingOperations.convertAndSend("/topic/game/room/"+game.getGameId(), game);
            } else {
                res.setMessage("WAITING");
                sendingOperations.convertAndSend("/topic/game/room/quick/"+ message.getSender(), res);
            }
        }

        else {
            if (message.getMessage().equals("GAME_START")) {
                System.out.println("GAME_START");
                Game game = gameService.startGame(message.getRoomId());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            } else if (message.getMessage().equals("GAME_INFO")) {
                Game game = gameService.findById(message.getRoomId());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            } else if (message.getMessage().equals("CHANGE_TEAM")) {
                Game game = gameService.findById(message.getRoomId());
                User userA = game.getRedTeam().getPlayer(message.getSender());
                User userB = game.getBlueTeam().getPlayer(message.getTargets());
                game.changeTeam(userA, userB);
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            } else {
                if (!gameService.findById(message.getRoomId()).isStarted()) {
                    System.out.println("게임 시작 안했음! 명령 무시함");
                    return;
                }
                Game game = gameService.findById(message.getRoomId());
                ResponseMessage res = gameService.playGame(message);
                res.setRedItemList(game.getRedPuzzle().getItemList());
                res.setBlueItemList(game.getBluePuzzle().getItemList());
                res.setRedProgressPercent((double) game.getRedPuzzle().getCorrectedCount() / (game.getRedPuzzle().getLengthCnt() * game.getRedPuzzle().getWidthCnt()) * 100);
                res.setBlueProgressPercent((double) game.getBluePuzzle().getCorrectedCount() / (game.getBluePuzzle().getLengthCnt() * game.getBluePuzzle().getWidthCnt()) * 100);

                res.setRedBundles(game.getRedPuzzle().getBundles());
                if (game.getGameType().equals("BATTLE")) {
                    res.setBlueBundles(game.getBluePuzzle().getBundles());
                }
                sendingOperations.convertAndSend("/topic/game/room/" + message.getRoomId(), res);
            }
        }
    }

    //서버 타이머  제공
    @Scheduled(fixedRate = 1000)
    public void sendServerTime() throws Exception {
        List<Game> allRoom = gameService.findAllCooperationRoom();
        allRoom.addAll(gameService.findAllBattleRoom());
        for (int i = allRoom.size()-1; i >= 0 ; i--) {
            if (allRoom.get(i).isStarted()) {
                long time = allRoom.get(i).getTime();
                if (allRoom.get(i).getGameType().equals("BATTLE")) {
                    time = BATTLE_TIMER-time;
                }
                if (time >= 0) {
                    Map<String, Long> timer = new HashMap<>();
                    timer.put("time", time);
                    sendingOperations.convertAndSend("/topic/game/room/" + allRoom.get(i).getGameId(), timer);
                } else {
                    if (allRoom.get(i).getGameType().equals("BATTLE")) {
                        gameService.save(allRoom.get(i));
                    }
                    sendingOperations.convertAndSend("/topic/game/room/" + allRoom.get(i).getGameId(), "너 게임 끝났어! 이 방 폭파됨");
                    gameService.deleteRoom(allRoom.get(i).getGameId());
                }
//                System.out.println(allRoom.get(i).getGameName() + "에 " + allRoom.get(i).getTime() + "초 라고 보냈음");
            }
        }
    }

    //배틀 드랍 아이템 제공
    //20초에 한번씩 제공하기로 함
    @Scheduled(fixedRate = 10000)
    public void sendDropItem() {
        List<Game> allRoom = gameService.findAllBattleRoom();
        Random random = new Random();
        for (int i = allRoom.size()-1; i >= 0 ; i--) {
            if (allRoom.get(i).isStarted()) {
                //확률 계산
                int possibility = random.nextInt(100);
                if (possibility <= 100) {
                    DropItem item = DropItem.randomCreate();
                    allRoom.get(i).getDropRandomItem().put(item.getUuid(), item);
                    ResponseMessage res = new ResponseMessage();
                    res.setMessage("DROP_ITEM");
                    res.setRandomItem(item);
                    sendingOperations.convertAndSend("/topic/game/room/" + allRoom.get(i).getGameId(), res);
                }
            }
        }
    }

    //60초에 한번씩 방 청소
    @Scheduled(fixedRate = 60000)
    public void deleteGame() {
        List<Game> allRoom = gameService.findAllBattleRoom();
        allRoom.addAll(gameService.findAllCooperationRoom());
        for (int i = allRoom.size()-1; i >= 0 ; i--) {
            if (allRoom.get(i).isEmpty()) {
                System.out.println(allRoom.get(i).getGameName() + " 방 삭제");
                gameService.deleteRoom(allRoom.get(i).getGameId());
            }
        }
    }
}

