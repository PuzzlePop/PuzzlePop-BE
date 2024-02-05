package com.ssafy.puzzlepop.gameinfo.advice;

import com.ssafy.puzzlepop.gameinfo.exception.GameInfoNotFoundException;
import com.ssafy.puzzlepop.gameinfo.exception.GameInfoValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameInfoExceptionHandler {

    @ExceptionHandler(GameInfoNotFoundException.class)
    public ResponseEntity<String> handleGameInfoNotFoundException(GameInfoNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameInfoValidationException.class)
    public ResponseEntity<String> handleGameInfoValidationException(GameInfoValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        // 예상치 못한 예외에 대한 처리
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
