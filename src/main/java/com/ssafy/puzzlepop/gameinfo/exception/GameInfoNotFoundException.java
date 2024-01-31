package com.ssafy.puzzlepop.gameinfo.exception;

public class GameInfoNotFoundException extends RuntimeException {
    public GameInfoNotFoundException(String message) {
        super(message);
    }

    public GameInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
