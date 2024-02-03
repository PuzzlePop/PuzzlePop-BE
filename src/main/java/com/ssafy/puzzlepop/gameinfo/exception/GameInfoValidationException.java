package com.ssafy.puzzlepop.gameinfo.exception;

public class GameInfoValidationException extends RuntimeException {

    public GameInfoValidationException(Long id) {
        super("Invalid GameInfo with id: " + id);
    }

    public GameInfoValidationException(String message) {
        super(message);
    }

    public GameInfoValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
