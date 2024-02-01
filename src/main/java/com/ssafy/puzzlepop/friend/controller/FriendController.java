package com.ssafy.puzzlepop.friend.controller;

import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.exception.FriendNotFoundException;
import com.ssafy.puzzlepop.friend.service.FriendService;
import com.ssafy.puzzlepop.user.domain.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friend")
    public ResponseEntity<?> getFriendById1AndId2(@RequestBody FriendDto requestDto) {
        try {
            FriendDto responseDto = friendService.getFriendById1AndId2(requestDto.getFromUserId(), requestDto.getToUserId());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/friend")
    public ResponseEntity<?> createFriend(@RequestBody FriendDto requestDto) {
        try {
            Long id = friendService.createFriend(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/friend")
    public ResponseEntity<?> updateFriend(@RequestBody FriendDto requestDto) {
        try {
            Long id = friendService.updateFriend(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/friend")
    public ResponseEntity<?> deleteFriend(@RequestBody FriendDto requestDto) {
        try {
            friendService.deleteFriend(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/friend/from")
    public ResponseEntity<?> getAllByFromUserIdAndRequestStatus(@RequestBody FriendDto requestDto) {
        try {
            List<FriendDto> responseDtos = friendService.getAllByFromUserIdAndRequestStatus(requestDto.getFromUserId(), requestDto.getRequestStatus());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/friend/to")
    public ResponseEntity<?> getAllByToUserIdAndRequestStatus(@RequestBody FriendDto requestDto) {
        try {
            List<FriendDto> responseDtos = friendService.getAllByToUserIdAndRequestStatus(requestDto.getToUserId(), requestDto.getRequestStatus());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/friend/list")
    public ResponseEntity<?> getAllByFromUserIdOrToUserId(@RequestBody UserDto requestDto) {
        try {
            List<UserDto> responseDtos = friendService.getAllByFromUserIdOrToUserId(requestDto.getId());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}