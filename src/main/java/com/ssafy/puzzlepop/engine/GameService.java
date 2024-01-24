package com.ssafy.puzzlepop.engine;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private Map<String, Game> chatRooms;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<Game> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<Game> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public Game findById(String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public Game createRoom(String name) {
        Game game = Game.create(name);
        chatRooms.put(game.getGameId(), game);
        return game;
    }
}
