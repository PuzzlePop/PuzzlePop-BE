package com.ssafy.puzzlepop.user.controller;

import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.domain.UserInfoDto;
import com.ssafy.puzzlepop.user.exception.UserNotFoundException;
import com.ssafy.puzzlepop.user.service.UserService;
import jdk.security.jarsigner.JarSigner;
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
        System.out.println("/user GET : getUserByEmail");
        System.out.println("id:"+id);
        try {
            UserDto responseDto = userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
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
            return ResponseEntity.status(HttpStatus.OK).body(id);
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
            List<UserInfoDto> responseDtos = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/search/email")
    public ResponseEntity<?> findUsersByEmail(@RequestParam String email) {
        System.out.println("/user/search/email GET");
        System.out.println("email: "+email);
        try{
            List<UserInfoDto> responseDtos = userService.getUsersByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/search/nickname")
    public ResponseEntity<?> findUsersByNickname(@RequestParam String nickname) {
        System.out.println("/user/search/nickname GET");
        System.out.println("nickname: "+nickname);
        try {
            List<UserInfoDto> responseDtos = userService.getUsersByNickname(nickname);
            System.out.println(responseDtos);
            return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
