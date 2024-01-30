package com.ssafy.puzzlepop.engine;

import com.ssafy.puzzlepop.engine.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    private final GameService gameService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("wsHandler = " + wsHandler);
        String roomId = extractRoomIdFromRequest(request); // 요청에서 방 번호 추출
        if (!isValidRoomId(roomId)) { // 방 번호가 유효하지 않으면
            response.setStatusCode(HttpStatus.FORBIDDEN); // 접속을 거부
            return false;
        }
        return true;
    }

    // 요청에서 방 번호 추출
    private String extractRoomIdFromRequest(ServerHttpRequest request) {
        // 요청에서 방 번호를 추출하는 로직을 구현
        // 실제 요청에서 방 번호를 추출하는 방법에 따라 구현이 달라질 수 있습니다.
        // 예를 들어, URI에서 파라미터로 받거나, 요청 헤더에서 특정 값을 찾는 등의 방법을 사용할 수 있습니다.
        // 여기서는 예시로 URI에서 roomId 부분을 추출하는 것으로 가정합니다.
        System.out.println(request.getURI());
        String uri = request.getURI().getPath();
        return uri.substring(uri.lastIndexOf('/') + 1);
    }

    // 클라이언트가 요청한 방 번호가 유효한지 확인
    private boolean isValidRoomId(String roomId) {
        // 여기에 방 번호가 유효한지 확인하는 로직을 구현
        // 특정한 방 번호 패턴을 허용하는 등의 방법으로 구현할 수 있습니다.
        return gameService.getGameRooms().containsKey(roomId);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
