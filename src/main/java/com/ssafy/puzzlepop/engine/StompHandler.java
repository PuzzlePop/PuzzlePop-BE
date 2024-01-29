package com.ssafy.puzzlepop.engine;

import com.ssafy.puzzlepop.engine.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final SubscriptionManager subscriptionManager;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("StompHandler.preSend");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println(accessor);
        System.out.println(accessor.getCommand());


        return message;
    }
}
