package com.ssafy.puzzlepop.teamuser.exception;

public class TeamUserNotFoundException extends RuntimeException {
    public TeamUserNotFoundException(Long id) {
        super("TeamUser Not Found with id: " + id);
    }

    public TeamUserNotFoundException(String message) {
        super(message);
    }

    public TeamUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
