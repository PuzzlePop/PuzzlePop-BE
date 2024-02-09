package com.ssafy.puzzlepop.teamuser.exception;

public class TeamUserValidationException extends RuntimeException {

    public TeamUserValidationException(Long id) {
        super("Invalid TeamUser with id: " + id);
    }

    public TeamUserValidationException(String message) {
        super(message);
    }

    public TeamUserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
