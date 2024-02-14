package com.ssafy.puzzlepop.user.controller;

import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.exception.UserNotFoundException;
import com.ssafy.puzzlepop.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/user")
    public ResponseEntity<?> getUserByEmail(@RequestParam Long id) {
        try {
            UserDto responseDto = userService.getUserById(id);
            return ResponseEntity.ok(responseDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDto requestDto) {
        try {
            Long id = userService.createUser(requestDto);
            return ResponseEntity.ok(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDto requestDto) {
        try {
            Long id = userService.updateUser(requestDto);
            return ResponseEntity.ok(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestBody UserDto requestDto) {
        try {
            userService.deleteUser(requestDto);
            return ResponseEntity.ok(requestDto.getId());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/user/list")
    public ResponseEntity<?> findAllUsers() {
        try {
            List<UserDto> responseDtos = userService.getAllUsers();
            return ResponseEntity.ok(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/user/search/email")
    public ResponseEntity<?> findUsersByEmail(@RequestBody String email) {
        System.out.println(email);
        try{
            List<UserDto> responseDtos = userService.getUsersByEmail(email);
            return ResponseEntity.ok(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/search/nickname")
    public ResponseEntity<?> findUsersByNickname(@RequestParam String nickname) {
        try {
            List<UserDto> responseDtos = userService.getUsersByNickname(nickname);
            return ResponseEntity.ok(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
