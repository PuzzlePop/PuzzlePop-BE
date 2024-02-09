package com.ssafy.puzzlepop.teamitem.exception;

public class TeamItemNotFoundException extends RuntimeException {

    public TeamItemNotFoundException(Long id) {
        super("TeamItem Not Found with id: " + id);
    }

    public TeamItemNotFoundException(String message) {
        super(message);
    }

    public TeamItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
