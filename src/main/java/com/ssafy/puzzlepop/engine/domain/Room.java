package com.ssafy.puzzlepop.engine.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Room {
    private String name;
    private String userid;
    private GameType type;
    private int roomSize;
}
