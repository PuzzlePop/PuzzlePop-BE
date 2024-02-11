package com.ssafy.puzzlepop.teamitem.exception;

public class TeamItemValidationException extends RuntimeException {

    public TeamItemValidationException(Long id) {
        super("Invalid TeamItem with id: " + id);
    }

    public TeamItemValidationException(String message) {
        super(message);
    }

    public TeamItemValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
