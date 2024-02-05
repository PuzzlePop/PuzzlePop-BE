package com.ssafy.puzzlepop.teamuser.controller;

import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserResponseDto;
import com.ssafy.puzzlepop.teamuser.service.TeamUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TeamUserController {
    private final TeamUserServiceImpl teamUserService;

    @GetMapping("/teamuser/byId")
    public ResponseEntity<?> readTeamUser(Long id) {
        TeamUserResponseDto responseDto = teamUserService.readTeamUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/teamuser/byTeamId")
    public ResponseEntity<?> findAllByTeamId(@RequestParam Long teamId) {
        List<TeamUserResponseDto> responseDtos = teamUserService.findAllByTeamId(teamId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("/teamuser/byUserId")
    public ResponseEntity<?> findAllByUserId(@RequestParam Long userId) {
        List<TeamUserResponseDto> responseDtos = teamUserService.findAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/teamuser")
    public ResponseEntity<?> createTeamUser(@RequestBody TeamUserRequestDto requestDto) {
        Long responseId = teamUserService.createTeamUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }

    @PutMapping("/teamuser")
    public ResponseEntity<?> updateTeamUser(@RequestBody TeamUserRequestDto requestDto) {
        Long responseId = teamUserService.updateTeamUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }

    @DeleteMapping("/teamuser")
    public ResponseEntity<?> deleteTeamUser(@RequestParam Long id) {
        Long responseId = teamUserService.deleteTeamUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }
}
