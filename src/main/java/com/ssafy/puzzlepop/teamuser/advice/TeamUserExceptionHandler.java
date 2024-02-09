package com.ssafy.puzzlepop.teamuser.advice;

import com.ssafy.puzzlepop.teamuser.exception.TeamUserNotFoundException;
import com.ssafy.puzzlepop.teamuser.exception.TeamUserValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class TeamUserExceptionHandler {

    @ExceptionHandler(TeamUserNotFoundException.class)
    public ResponseEntity<String> handleTeamNotFoundException(TeamUserNotFoundException e) {
        log.error("TeamUserExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamUserValidationException.class)
    public ResponseEntity<String> handleTeamValidationException(TeamUserValidationException e) {
        log.error("TeamUserExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
