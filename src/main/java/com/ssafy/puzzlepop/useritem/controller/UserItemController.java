package com.ssafy.puzzlepop.useritem.controller;

import com.ssafy.puzzlepop.useritem.domain.UserItemRequestDto;
import com.ssafy.puzzlepop.useritem.domain.UserItemResponseDto;
import com.ssafy.puzzlepop.useritem.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.useritem.exception.UserItemNotFoundException;
import com.ssafy.puzzlepop.useritem.exception.UserNotFoundException;
import com.ssafy.puzzlepop.useritem.service.UserItemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
public class UserItemController {
    private final UserItemServiceImpl userItemService;

    @GetMapping("/useritem")
    public ResponseEntity<?> getUserItemById(@RequestParam("id") Long id) {
        try {
            UserItemResponseDto responseDto = userItemService.getUserItemById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (UserNotFoundException | ItemNotFoundException | UserItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/useritem")
    public ResponseEntity<?> createUserItem(@RequestBody UserItemRequestDto requestDto) {
        try {
            Long userId = requestDto.getUserId();
            Long itemId = requestDto.getItemId();
            Long id = userItemService.createUserItem(userId, itemId);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (UserNotFoundException | ItemNotFoundException | UserItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/useritem")
    public ResponseEntity<?> updateUserItem(@RequestParam("id") Long id,
                                            @RequestParam("itemId") Long itemId) {
        try {
            userItemService.updateUserItem(id, itemId);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (UserNotFoundException | ItemNotFoundException | UserItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/useritem")
    public ResponseEntity<?> deleteUserItem(@RequestParam Long id) {
        try {
            userItemService.deleteUserItem(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (UserNotFoundException | ItemNotFoundException | UserItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/useritem/search")
    public ResponseEntity<?> getUserItemsByUserId(@RequestParam("userId") Long userId) {
        try {
            List<UserItemResponseDto> responseDtos = userItemService.getUserItemsByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (UserNotFoundException | ItemNotFoundException | UserItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
