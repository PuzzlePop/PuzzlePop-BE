package com.ssafy.puzzlepop.engine.service;

import com.ssafy.puzzlepop.engine.domain.Game;
import com.ssafy.puzzlepop.engine.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private Map<String, Game> gameRooms;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        gameRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<Game> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<Game> result = new ArrayList<>(gameRooms.values());
        Collections.reverse(result);

        return result;
    }

    public Game findById(String roomId) {
        return gameRooms.get(roomId);
    }

    //채팅방 생성
    public Game createRoom(String name, String userid) {
        Game game = Game.create(name, userid);
        gameRooms.put(game.getGameId(), game);
        return game;
    }

//    public Game createRoom(String name, String userid, GameType type) {
//        Game game = Game.create(name, userid, type);
//        gameRooms.put(game.getGameId(), game);
//        return game;
//    }

    public Game startGame(String roomId) {
        Game game = findById(roomId);
        game.start();
        return game;
    }

    public Game playGame(String roomId, String message, String targets) {
        Game game = findById(roomId);

        //TODO
        //게임 진행 로직
        if (message.equals("add piece")) {

        }

        return game;
    }

    public boolean enterGame(String gameId, String userId, String sessionId) {
        Game game = gameRooms.get(gameId);
        if (game.enterPlayer(new User(userId), sessionId)) {
            return true;
        }

        return false;
    }
}
