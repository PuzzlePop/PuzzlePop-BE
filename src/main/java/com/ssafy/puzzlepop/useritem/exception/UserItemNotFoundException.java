package com.ssafy.puzzlepop.useritem.exception;

public class UserItemNotFoundException extends RuntimeException {

    public UserItemNotFoundException(String message){
        super(message);
    }

    public UserItemNotFoundException(Throwable cause ){
        super(cause);
    }

    public UserItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
