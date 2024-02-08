package com.ssafy.puzzlepop.engine;

import com.ssafy.puzzlepop.engine.controller.MessageController;
import com.ssafy.puzzlepop.engine.domain.Game;
import com.ssafy.puzzlepop.engine.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final GameService gameService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        String gameId = gameService.sessionToGame.get(sessionId);

        Game game = gameService.findById(gameId);

        if (game.isFinished()) {
            return message;
        } else {
            return null;
        }
    }
}
