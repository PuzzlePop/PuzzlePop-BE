package com.ssafy.puzzlepop.gameinfo.controller;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.exception.GameInfoNotFoundException;
import com.ssafy.puzzlepop.gameinfo.service.GameInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class GameInfoController {
    private final GameInfoService gameService;

    @GetMapping("/game")
    public ResponseEntity<?> getGameById(@RequestBody GameInfoDto requestDto) {
        try {
            GameInfoDto responseDto = gameService.getGameById(requestDto.getId());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
        } catch (GameInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/game")
    public ResponseEntity<?> createGame(@RequestBody GameInfoDto requestDto) {
        try {
            Long id = gameService.createGameInfo(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (GameInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/game")
    public ResponseEntity<?> updateGame(@RequestBody GameInfoDto requestDto) {
        try {
            Long id = gameService.updateGameInfo(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (GameInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/game")
    public ResponseEntity<?> deleteGame(@RequestBody GameInfoDto requestDto) {
        try {
            gameService.deleteGameInfo(requestDto.getId());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (GameInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/game/list")
    public ResponseEntity<?> findAllGames() {
        try {
            List<GameInfoDto> responseDtos = gameService.getAllGameInfos();
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (GameInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/game/search")
    public ResponseEntity<?> findGamesByType(@RequestBody GameInfoDto requestDto) {
        try {
            List<GameInfoDto> responseDtos = gameService.findAllByType(requestDto.getType());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (GameInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
