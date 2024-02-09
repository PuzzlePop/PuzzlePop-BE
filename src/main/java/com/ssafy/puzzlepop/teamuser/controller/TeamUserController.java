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

    @GetMapping("/teamuser")
    public ResponseEntity<?> readTeamUser(Long id) {
        TeamUserResponseDto responseDto = teamUserService.readTeamUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/teamuser/search")
    public ResponseEntity<?> findAllByFilter(@RequestParam String filter, @RequestParam Long id) {
        List<TeamUserResponseDto> responseDtos = switch (filter) {
            case "teamId" -> teamUserService.findAllByTeamId(id);
            case "userId" -> teamUserService.findAllByUserId(id);
            default -> null;
        };
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
