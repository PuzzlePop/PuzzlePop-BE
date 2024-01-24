package com.ssafy.puzzlepop.engine;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InGameMessage {

    private MessageType type;

    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;
}
