package com.ssafy.puzzlepop.friend.controller;

import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.domain.FriendRequestRespondDto;
import com.ssafy.puzzlepop.friend.domain.FriendUserInfoDto;
import com.ssafy.puzzlepop.friend.exception.FriendNotFoundException;
import com.ssafy.puzzlepop.friend.service.FriendService;
import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@CrossOrigin("*")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("/friend/{id1}/{id2}")
    public ResponseEntity<?> getFriendById1AndId2(@PathVariable("id1") Long id1,
                                                  @PathVariable("id2") Long id2) {
        try {
            FriendDto responseDto = friendService.getFriendById1AndId2(id1, id2);
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

    @PostMapping("/friend/from")
    public ResponseEntity<?> getAllByFromUserIdAndRequestStatus(@RequestBody FriendDto requestDto) {
        try {
            List<FriendDto> responseDtos = friendService.getAllByFromUserIdAndRequestStatus(requestDto.getFromUserId(), requestDto.getRequestStatus());
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/friend/to")
    public ResponseEntity<?> getAllByToUserIdAndRequestStatus(@RequestBody FriendDto requestDto) {
        try {
            List<FriendDto> responseDtos = friendService.getAllByToUserIdAndRequestStatus(requestDto.getToUserId(), requestDto.getRequestStatus());
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/friend/list")
    public ResponseEntity<?> getAllByFromUserIdOrToUserId(@RequestBody UserDto requestDto) {
        try {
            List<FriendDto> responseDtos = friendService.getAllByFromUserIdOrToUserId(requestDto.getId());
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/friend/list/all")
    public ResponseEntity<?> getAllByUserId(@RequestBody UserDto requestDto) {
        try {
            List<Long> friendIds = friendService.getAllFriendIdByUserId(requestDto.getId());
            List<UserDto> responseDtos = friendIds.stream()
                    .map(userService::getUserById).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (FriendNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/friend/list/{status}")
    public ResponseEntity<?> getFriendsByUserIdAndStatus(@PathVariable String status, @RequestBody UserDto requestDto) {
        try {
            List<FriendUserInfoDto> friendList = friendService.getFriendsByUserIdAndStatus(requestDto.getId(), status);
            return ResponseEntity.status(HttpStatus.OK).body(friendList);
        } catch (Exception e) {
//            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/friend/respond")
    public ResponseEntity<?> updateRequestStatus(@RequestBody FriendRequestRespondDto respondDto) {
        FriendDto updatedDto = friendService.updateRequestStatus(respondDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
}