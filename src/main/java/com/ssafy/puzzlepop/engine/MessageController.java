package com.ssafy.puzzlepop.engine;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private GameService gameService;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/game/message")
    public void enter(InGameMessage message) {
        if (InGameMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }

        if (message.getType().equals(InGameMessage.MessageType.ENTER)) {
            System.out.println(gameService.findById(message.getRoomId()).getGameName() + "에 " + message.getSender() + "님이 입장하셨습니다.");
            gameService.findById(message.getRoomId()).getRedTeam().addPlayer(new User(message.getSender()));
            sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(),message);
        } else {
            System.out.println("게임 명령어");
            System.out.println(message.getMessage());

            if (message.getMessage().equals("gameStart")) {
                Game game = gameService.startGame(message.getRoomId());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            } else {
                Game game = gameService.playGame(message.getRoomId(), message.getMessage());
                sendingOperations.convertAndSend("/topic/game/room/"+message.getRoomId(), game);
            }
        }


    }
}

