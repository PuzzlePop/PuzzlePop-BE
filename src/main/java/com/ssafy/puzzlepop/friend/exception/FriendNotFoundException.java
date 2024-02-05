package com.ssafy.puzzlepop.friend.exception;

public class FriendNotFoundException extends RuntimeException{
    public FriendNotFoundException(String message){
        super(message);
    }

    public FriendNotFoundException(Throwable cause ){
        super(cause);
    }

    public FriendNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
