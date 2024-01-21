package com.ssafy.puzzlepop.game.controller;

import com.ssafy.puzzlepop.game.domain.GameDto;
import com.ssafy.puzzlepop.game.exception.GameNotFoundException;
import com.ssafy.puzzlepop.game.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping("/game")
    public ResponseEntity<?> getGameById(@RequestBody GameDto requestDto) {
        try {
            GameDto responseDto = gameService.getGameById(requestDto.getId());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/game")
    public ResponseEntity<?> createGame(@RequestBody GameDto requestDto) {
        try {
            Long id = gameService.createGame(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/game")
    public ResponseEntity<?> updateGame(@RequestBody GameDto requestDto) {
        try {
            Long id = gameService.updateGame(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/game")
    public ResponseEntity<?> deleteGame(@RequestBody GameDto requestDto) {
        try {
            gameService.deleteGame(requestDto.getId());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/game/list")
    public ResponseEntity<?> findAllGames() {
        try {
            List<GameDto> responseDtos = gameService.getAllGames();
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/game/search")
    public ResponseEntity<?> findGamesByType(@RequestBody GameDto requestDto) {
        try {
            List<GameDto> responseDtos = gameService.findAllByType(requestDto.getType());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
