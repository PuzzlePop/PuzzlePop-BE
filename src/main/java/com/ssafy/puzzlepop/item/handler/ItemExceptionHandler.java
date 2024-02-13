package com.ssafy.puzzlepop.item.handler;

import com.ssafy.puzzlepop.item.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.item.exception.ItemValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ItemExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFoundException(ItemNotFoundException e) {
        log.error("ItemExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemValidationException.class)
    public ResponseEntity<String> handleItemValidationException(ItemValidationException e) {
        log.error("ItemExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
