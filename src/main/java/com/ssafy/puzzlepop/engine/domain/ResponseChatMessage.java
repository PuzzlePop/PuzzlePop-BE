package com.ssafy.puzzlepop.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseChatMessage {
    private String userid;
    private String teamColor;
    private String chatMessage;
    private Date time;
}
