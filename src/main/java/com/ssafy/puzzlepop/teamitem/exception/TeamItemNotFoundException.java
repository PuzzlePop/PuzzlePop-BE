package com.ssafy.puzzlepop.teamitem.exception;

public class TeamItemNotFoundException extends RuntimeException {
    public TeamItemNotFoundException(String message) {
        super(message);
    }

    public TeamItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
