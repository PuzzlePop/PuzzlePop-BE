package com.ssafy.puzzlepop.engine;

import com.ssafy.puzzlepop.engine.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class GameSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;
    private final GameService gameService;
    private final CustomHandshakeInterceptor customHandshakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/game/{roomId}")
                .setAllowedOriginPatterns("*")
                .addInterceptors(customHandshakeInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // app로 시작되는 메시지는 message-handling methods로 라우팅된다.
        registry.setApplicationDestinationPrefixes("/app");

        //sub으로 시작되는 요청을 구독한 모든 사용자들에게 메시지를 broadcast한다.
        registry.enableSimpleBroker("/queue", "/topic");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

    // 클라이언트가 채팅방에 접속하려는 URL이 유효한지 확인
    private boolean isValidRoomId(String roomId) {
        // 여기서 필요한 로직을 구현하여 유효한 방 번호인지 확인
        if (gameService.findById(roomId) == null) {
            return false;
        }

        return gameService.getGameRooms().containsKey(roomId);// 예시: 허용된 방 번호 집합에 해당 방 번호가 포함되어 있는지 확인
    }
}
