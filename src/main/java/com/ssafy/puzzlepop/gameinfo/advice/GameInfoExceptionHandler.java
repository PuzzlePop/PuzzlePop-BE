package com.ssafy.puzzlepop.gameinfo.advice;

import com.ssafy.puzzlepop.gameinfo.exception.GameInfoNotFoundException;
import com.ssafy.puzzlepop.gameinfo.exception.GameInfoValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GameInfoExceptionHandler {

    @ExceptionHandler(GameInfoNotFoundException.class)
    public ResponseEntity<String> handleGameInfoNotFoundException(GameInfoNotFoundException e) {
        log.error("GameInfoExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameInfoValidationException.class)
    public ResponseEntity<String> handleGameInfoValidationException(GameInfoValidationException e) {
        log.error("GameInfoExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
