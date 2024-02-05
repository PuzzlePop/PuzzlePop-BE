package com.ssafy.puzzlepop.useritem.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
    public ItemNotFoundException(Throwable cause ){
        super(cause);
    }
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
