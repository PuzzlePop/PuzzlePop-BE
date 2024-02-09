package com.ssafy.puzzlepop.team.exception;

public class TeamValidationException extends RuntimeException {

    public TeamValidationException(Long id) {
        super("Invalid Team with id: " + id);
    }

    public TeamValidationException(String message) {
        super(message);
    }

    public TeamValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
