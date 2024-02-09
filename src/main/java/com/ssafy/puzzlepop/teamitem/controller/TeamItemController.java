package com.ssafy.puzzlepop.teamitem.controller;

import com.ssafy.puzzlepop.teamitem.domain.TeamItemRequestDto;
import com.ssafy.puzzlepop.teamitem.domain.TeamItemResponseDto;
import com.ssafy.puzzlepop.teamitem.service.TeamItemServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class TeamItemController {
    private final TeamItemServiceImpl teamItemService;

    @GetMapping("/teamitem")
    public ResponseEntity<?> readTeamItem(@RequestParam Long id) {
        log.info("TeamItemController - readTeamItem(): id = " + id);
        TeamItemResponseDto responseDto = teamItemService.readTeamItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/teamitem/search")
    public ResponseEntity<?> findAllByFilter(@RequestParam String filter, @RequestParam Long id) {
        log.info("TeamItemController - findAllByFilter(): filter = " + filter + ", id = " + id);
        List<TeamItemResponseDto> responseDtos = switch (filter) {
            case "teamId" -> teamItemService.findAllByTeamId(id);
            case "itemId" -> teamItemService.findAllByItemId(id);
            default -> null;
        };
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/teamitem")
    public ResponseEntity<?> createTeamItem(@RequestBody TeamItemRequestDto requestDto) {
        log.info("TeamItemController - createTeamItem(): requestDto = " + requestDto);
        Long responseId = teamItemService.createTeamItem(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseId);
    }

    @PutMapping("/teamitem")
    public ResponseEntity<?> updateTeamItem(@RequestBody TeamItemRequestDto requestDto) {
        log.info("TeamItemController - updateTeamItem(): requestDto = " + requestDto);
        Long responseId = teamItemService.updateTeamItem(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }

    @DeleteMapping("/teamitem")
    public ResponseEntity<?> deleteTeamItem(@RequestParam Long id) {
        log.info("TeamItemController - deleteTeamItem(): id = " + id);
        Long responseId = teamItemService.deleteTeamItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseId);
    }

}
