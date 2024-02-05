package com.ssafy.puzzlepop.gameinfo.controller;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.service.GameInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameInfoController {
    private final GameInfoService gameInfoService;

    @GetMapping("/gameinfo")
    public ResponseEntity<?> readGameInfo(@RequestParam Long gameId) {
        GameInfoDto responseDto = gameInfoService.readGameInfo(gameId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
    }

    @GetMapping("/gameinfo/all")
    public ResponseEntity<?> readAllGameInfo() {
        List<GameInfoDto> responseDtos = gameInfoService.readAllGameInfos();
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/gameinfo/search")
    public ResponseEntity<?> findAllByFilter(@RequestParam String filter,
                                             @RequestParam String value) {
        List<GameInfoDto> responseDtos = switch (filter) {
            case "type" -> gameInfoService.findAllByType(value);
            case "isCleared" -> gameInfoService.findAllByIsCleared(Boolean.parseBoolean(value));
            case "curPlayerCount" -> gameInfoService.findAllByCurPlayerCount(Integer.parseInt(value));
            case "maxPlayerCount" -> gameInfoService.findAllByMaxPlayerCount(Integer.parseInt(value));
            case "totalPieceCount" -> gameInfoService.findAllByTotalPieceCount(Integer.parseInt(value));
            default -> null;
        };
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/gameinfo")
    public ResponseEntity<?> createGame(@RequestBody GameInfoDto requestDto) {
        Long id = gameInfoService.createGameInfo(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/gameinfo")
    public ResponseEntity<?> updateGame(@RequestBody GameInfoDto requestDto) {
        Long id = gameInfoService.updateGameInfo(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/gameinfo")
    public ResponseEntity<?> deleteGame(@RequestParam Long id) {
        id = gameInfoService.deleteGameInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
