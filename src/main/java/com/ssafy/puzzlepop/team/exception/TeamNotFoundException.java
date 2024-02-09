package com.ssafy.puzzlepop.team.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long id) {
        super("Team Not Found with id: " + id);
    }

    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
