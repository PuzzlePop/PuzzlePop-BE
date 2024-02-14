package com.ssafy.puzzlepop.engine;

import com.ssafy.puzzlepop.engine.domain.Game;
import com.ssafy.puzzlepop.engine.domain.User;
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
//        System.out.println("CustomHandshakeInterceptor.beforeHandshake");
//        String userId = extractUserIdFromRequest(request); // 요청에서 유저 세션 추출
//
//        String gameId = gameService.sessionToGame.get(userId);
//        Game game = gameService.findById(gameId);
//        if (game == null) {
//            return true;
//        }
//
//        if (game.isStarted() && !game.isFinished()) {
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }


    // 유저 세션 추출
    private String extractUserIdFromRequest(ServerHttpRequest request) {
        String uri = request.getURI().getPath();
        System.out.println(uri);
        String[] tmp = uri.split("/");
        return tmp[3];
    }

    // 클라이언트가 요청한 방 번호가 유효한지 확인
    private boolean isValidRoomId(String roomId) {
        // 여기에 방 번호가 유효한지 확인하는 로직
        return gameService.getGameRooms().containsKey(roomId);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
