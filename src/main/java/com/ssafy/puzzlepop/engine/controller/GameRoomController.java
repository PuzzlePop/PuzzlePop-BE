package com.ssafy.puzzlepop.engine.controller;

import com.ssafy.puzzlepop.engine.domain.Game;
import com.ssafy.puzzlepop.engine.domain.Room;
import com.ssafy.puzzlepop.engine.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
@CrossOrigin("*")
public class GameRoomController {

    private final GameService gameService;

    //협동 게임 방 리스트
    @GetMapping("/rooms/cooperation")
    @ResponseBody
    public ResponseEntity<?> cooperationRooms() {
        List<Game> allCooperationRoom = gameService.findAllCooperationRoom();
        return ResponseEntity.ok(allCooperationRoom);
    }

    //배틀 게임 방 리스트
    @GetMapping("/rooms/battle")
    @ResponseBody
    public ResponseEntity<?> battleRooms() {
        List<Game> allBattleRoom = gameService.findAllBattleRoom();
        return ResponseEntity.ok(allBattleRoom);
    }

    // 모든 채팅방 목록 반환
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public Game createRoom(@RequestBody Room room) {
        return gameService.createRoom(room);
    }

    // 특정 게임방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<?> roomInfo(@PathVariable String roomId) {
        Game game = gameService.findById(roomId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        } else {
            if (game.isStarted()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game is started");
            }

            if (game.getGameType().equals("BATTLE")) {
                if (game.getRedTeam().getPlayers().size() + game.getBlueTeam().getPlayers().size() == game.getRoomSize()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Room is fulled");
                }

                return ResponseEntity.ok(game);
            } else {
                if (game.getRedTeam().getPlayers().size() == game.getRoomSize()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Room is fulled");
                }

                return ResponseEntity.ok(game);
            }
        }
    }
}
