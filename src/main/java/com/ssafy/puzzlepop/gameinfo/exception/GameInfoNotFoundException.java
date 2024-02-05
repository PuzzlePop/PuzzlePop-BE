package com.ssafy.puzzlepop.gameinfo.exception;

public class GameInfoNotFoundException extends RuntimeException {

    public GameInfoNotFoundException(Long id) {
        super("GameInfo Not Found with id: " + id);
    }

    public GameInfoNotFoundException(String message) {
        super(message);
    }

    public GameInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
