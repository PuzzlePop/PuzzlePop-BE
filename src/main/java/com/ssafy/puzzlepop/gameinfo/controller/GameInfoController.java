package com.ssafy.puzzlepop.gameinfo.controller;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.service.GameInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GameInfoController {
    private final GameInfoService gameInfoService;

    @GetMapping("/gameinfo")
    public ResponseEntity<?> readGameInfo(@RequestParam Long gameId) {
        log.info("GameInfoController - readGameInfo(): id = " + gameId);
        GameInfoDto responseDto = gameInfoService.readGameInfo(gameId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
    }

    @GetMapping("/gameinfo/all")
    public ResponseEntity<?> readAllGameInfo() {
        log.info("GameInfoController - readAllGameInfo()");
        List<GameInfoDto> responseDtos = gameInfoService.readAllGameInfos();
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/gameinfo/search")
    public ResponseEntity<?> findAllByFilter(@RequestParam String filter,
                                             @RequestParam String value) {
        log.info("GameInfoController - findAllByFilter(): filter = " + filter + ", value = " + value);
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
    public ResponseEntity<?> createGameInfo(@RequestBody GameInfoDto requestDto) {
        log.info("GameInfoController - createGameInfo(): requestDto = " + requestDto);
        Long id = gameInfoService.createGameInfo(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/gameinfo")
    public ResponseEntity<?> updateGameInfo(@RequestBody GameInfoDto requestDto) {
        log.info("GameInfoController - updateGameInfo(): requestDto = " + requestDto);
        Long id = gameInfoService.updateGameInfo(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/gameinfo")
    public ResponseEntity<?> deleteGameInfo(@RequestParam Long id) {
        log.info("GameInfoController - deleteGameInfo(): id = " + id);
        id = gameInfoService.deleteGameInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
