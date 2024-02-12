package com.ssafy.puzzlepop.team.controller;

import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.team.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.team.service.TeamServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class TeamController {
    private final TeamServiceImpl teamService;

    @GetMapping("/team")
    public ResponseEntity<?> readTeam(@RequestParam Long id) {
        log.info("TeamController - readTeam(): id = " + id);
        TeamDto responseDto = teamService.readTeam(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/team/all")
    public ResponseEntity<?> readAllTeams() {
        log.info("TeamController - readAllTeams()");
        List<TeamDto> responseDtos = teamService.readAllTeams();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody TeamDto requestDto) {
        log.info("TeamController - createTeam(): requestDto = " + requestDto);
        Long id = teamService.createTeam(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/team")
    public ResponseEntity<?> updateTeam(@RequestBody TeamDto requestDto) {
        log.info("TeamController - updateTeam(): requestDto = " + requestDto);
        Long id = teamService.updateTeam(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/team")
    public ResponseEntity<?> deleteTeam(@RequestParam Long id) {
        log.info("TeamController - deleteTeam(): id = " + id);
        id = teamService.deleteTeam(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/team/search")
    public ResponseEntity<?> findTeamsByGameId(@RequestParam Long gameId) {
        log.info("TeamController - findTeamsByGameId(): gameId = " + gameId);
        List<TeamDto> responseDtos = teamService.findAllByGameId(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @PutMapping("/team/update")
    public ResponseEntity<?> updateMatchedPieceCount(@RequestBody TeamDto requestDto) {
        log.info("TeamController - updateTeam(): requestDto = " + requestDto);
        Long id = teamService.updateMatchedPieceCount(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
