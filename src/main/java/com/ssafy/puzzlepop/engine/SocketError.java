package com.ssafy.puzzlepop.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SocketError {
    private String type;
    private String message;
}
