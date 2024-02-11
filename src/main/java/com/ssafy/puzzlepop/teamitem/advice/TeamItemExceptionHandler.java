package com.ssafy.puzzlepop.teamitem.advice;

import com.ssafy.puzzlepop.teamitem.exception.TeamItemNotFoundException;
import com.ssafy.puzzlepop.teamitem.exception.TeamItemValidationException;
import com.ssafy.puzzlepop.teamuser.exception.TeamUserNotFoundException;
import com.ssafy.puzzlepop.teamuser.exception.TeamUserValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class TeamItemExceptionHandler {

    @ExceptionHandler(TeamUserNotFoundException.class)
    public ResponseEntity<String> handleTeamNotFoundException(TeamItemNotFoundException e) {
        log.error("TeamItemExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamUserValidationException.class)
    public ResponseEntity<String> handleTeamValidationException(TeamItemValidationException e) {
        log.error("TeamItemExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
