package com.ssafy.puzzlepop.teamuser.advice;

import com.ssafy.puzzlepop.teamuser.exception.TeamUserNotFoundException;
import com.ssafy.puzzlepop.teamuser.exception.TeamUserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TeamUserExceptionHandler {

    @ExceptionHandler(TeamUserNotFoundException.class)
    public ResponseEntity<String> handleTeamNotFoundException(TeamUserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamUserValidationException.class)
    public ResponseEntity<String> handleTeamValidationException(TeamUserValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        // 예상치 못한 예외에 대한 처리
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
