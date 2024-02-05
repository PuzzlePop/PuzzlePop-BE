package com.ssafy.puzzlepop.teamitem.controller;

import com.ssafy.puzzlepop.teamitem.domain.TeamItemRequestDto;
import com.ssafy.puzzlepop.teamitem.domain.TeamItemResponseDto;
import com.ssafy.puzzlepop.teamitem.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.teamitem.exception.TeamItemNotFoundException;
import com.ssafy.puzzlepop.teamitem.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.teamitem.service.TeamItemService;
import com.ssafy.puzzlepop.teamitem.service.TeamItemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TeamItemController {
    private final TeamItemServiceImpl teamItemService;

    @GetMapping("/teamitem")
    public ResponseEntity<?> getTeamItemById(@RequestParam("id") Long id) {
        try {
            TeamItemResponseDto responseDto = teamItemService.getTeamItemById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (TeamNotFoundException | ItemNotFoundException | TeamItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/teamitem")
    public ResponseEntity<?> createTeamItem(@RequestBody TeamItemRequestDto requestDto) {
        try {
            Long teamId = requestDto.getTeamId();
            Long itemId = requestDto.getItemId();
            Long id = teamItemService.createTeamItem(teamId, itemId);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (TeamNotFoundException | ItemNotFoundException | TeamItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/teamitem")
    public ResponseEntity<?> updateTeamItem(@RequestParam("id") Long id,
                                            @RequestParam("itemId") Long itemId) {
        try {
            teamItemService.updateTeamItem(id, itemId);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (TeamNotFoundException | ItemNotFoundException | TeamItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/teamitem")
    public ResponseEntity<?> deleteTeamItem(@RequestParam Long id) {
        try {
            teamItemService.deleteTeamItem(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (TeamNotFoundException | ItemNotFoundException | TeamItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/teamitem/search")
    public ResponseEntity<?> getTeamItemsByTeamId(@RequestParam("teamId") Long teamId) {
        try {
            List<TeamItemResponseDto> responseDtos = teamItemService.getTeamItemsByTeamId(teamId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (TeamNotFoundException | ItemNotFoundException | TeamItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
