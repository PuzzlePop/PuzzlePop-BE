package com.ssafy.puzzlepop.item.exception;

public class ItemValidationException extends RuntimeException {

    public ItemValidationException(Long id) {
        super("Invalid Item with id: " + id);
    }

    public ItemValidationException(String message) {
        super(message);
    }

    public ItemValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
