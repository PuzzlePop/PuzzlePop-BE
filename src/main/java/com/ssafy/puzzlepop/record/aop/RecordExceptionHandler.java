package com.ssafy.puzzlepop.record.aop;

import com.ssafy.puzzlepop.record.exception.RecordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecordExceptionHandler {

    @ExceptionHandler(RecordException.class)
    public ResponseEntity<?> handleRecordException(Exception e) {
        e.printStackTrace(); // TODO: 이거 지워야함
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
