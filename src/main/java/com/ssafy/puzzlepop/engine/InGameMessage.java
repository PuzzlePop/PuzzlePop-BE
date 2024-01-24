package com.ssafy.puzzlepop.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InGameMessage {

    public enum MessageType {
        ENTER, GAME
    }

    private MessageType type;

    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;
    //명령어
    private String command;
}
