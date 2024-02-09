package com.ssafy.puzzlepop.team.controller;

import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.team.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.team.service.TeamServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TeamController {
    private final TeamServiceImpl teamService;

    @GetMapping("/team")
    public ResponseEntity<?> readTeam(@RequestParam Long id) {
        TeamDto responseDto = teamService.readTeam(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
    }

    @GetMapping("/team/all")
    public ResponseEntity<?> readAllTeams() {
        List<TeamDto> responseDtos = teamService.readAllTeams();
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody TeamDto requestDto) {
        Long id = teamService.createTeam(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/team")
    public ResponseEntity<?> updateTeam(@RequestBody TeamDto requestDto) {
        Long id = teamService.updateTeam(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/team")
    public ResponseEntity<?> deleteTeam(@RequestParam Long id) {
        id = teamService.deleteTeam(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/team/search")
    public ResponseEntity<?> findTeamsByGameId(@RequestParam Long gameId) {
        List<TeamDto> responseDtos = teamService.findAllByGameId(gameId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }

    @PutMapping("/team/update")
    public ResponseEntity<?> updateMatchedPieceCount(@RequestBody TeamDto requestDto) {
        Long id = teamService.updateMatchedPieceCount(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
