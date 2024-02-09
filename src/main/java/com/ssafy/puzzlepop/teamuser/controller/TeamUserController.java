package com.ssafy.puzzlepop.teamuser.controller;

import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserResponseDto;
import com.ssafy.puzzlepop.teamuser.service.TeamUserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class TeamUserController {
    private final TeamUserServiceImpl teamUserService;

    @GetMapping("/teamuser")
    public ResponseEntity<?> readTeamUser(Long id) {
        log.info("TeamUserController - readTeamUser(): id = " + id);
        TeamUserResponseDto responseDto = teamUserService.readTeamUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/teamuser/search")
    public ResponseEntity<?> findAllByFilter(@RequestParam String filter, @RequestParam Long id) {
        log.info("TeamUserController - findAllByFilter(): filter = " + filter + ", id = " + id);
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
        log.info("TeamUserController - createTeamUser(): requestDto = " + requestDto);
        Long responseId = teamUserService.createTeamUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }

    @PutMapping("/teamuser")
    public ResponseEntity<?> updateTeamUser(@RequestBody TeamUserRequestDto requestDto) {
        log.info("TeamUserController - updateTeamUser(): requestDto = " + requestDto);
        Long responseId = teamUserService.updateTeamUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }

    @DeleteMapping("/teamuser")
    public ResponseEntity<?> deleteTeamUser(@RequestParam Long id) {
        log.info("TeamUserController - deleteTeamUser(): id = " + id);
        Long responseId = teamUserService.deleteTeamUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }
}
