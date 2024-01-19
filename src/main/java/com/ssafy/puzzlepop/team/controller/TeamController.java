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
    public ResponseEntity<?> getTeamById(@RequestBody TeamDto requestDto) {
        try {
            TeamDto responseDto = teamService.getTeamById(requestDto.getId());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
        } catch (TeamNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody TeamDto requestDto) {
        try {
            Long id = teamService.createTeam(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (TeamNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/team")
    public ResponseEntity<?> updateTeam(@RequestBody TeamDto requestDto) {
        try {
            Long id = teamService.updateTeam(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (TeamNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/team")
    public ResponseEntity<?> deleteTeam(@RequestBody TeamDto requestDto) {
        try {
            teamService.deleteTeam(requestDto.getId());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (TeamNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/team/list")
    public ResponseEntity<?> findAllTeams() {
        try {
            List<TeamDto> responseDtos = teamService.getAllTeams();
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (TeamNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/team/search")
    public ResponseEntity<?> findTeamByGameId(@RequestBody TeamDto requestDto) {
        try {
            List<TeamDto> responseDtos = teamService.findAllByGameId(requestDto.getGameId());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (TeamNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
