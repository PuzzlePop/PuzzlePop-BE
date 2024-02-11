package com.ssafy.puzzlepop.user.controller;

import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.exception.UserNotFoundException;
import com.ssafy.puzzlepop.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;



//    @PostMapping("/user")
//    public ResponseEntity<?> createUser(@RequestBody UserDto requestDto) {
//        try {
//            Long id = userService.createUser(requestDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(id);
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserByEmail(@RequestBody UserDto requestDto) {
        try {
            UserDto responseDto = userService.getUserByEmail(requestDto.getEmail());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
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
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
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
            return ResponseEntity.status(HttpStatus.OK).body(id);
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
            return ResponseEntity.status(HttpStatus.OK).body(requestDto.getId());
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

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "http://localhost:5173/");
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/search/email")
    public ResponseEntity<?> findUserByEmail(@RequestBody UserDto requestDto) {
        try{
            UserDto responseDto = userService.getUserByEmail(requestDto.getEmail());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/search/nickname")
    public ResponseEntity<?> findUsersByNickname(@RequestBody UserDto requestDto) {
        try {
            List<UserDto> responseDtos = userService.getUsersByNickname(requestDto.getNickname());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
