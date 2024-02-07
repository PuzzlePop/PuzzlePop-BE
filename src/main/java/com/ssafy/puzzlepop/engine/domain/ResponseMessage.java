package com.ssafy.puzzlepop.engine.domain;

import lombok.*;

import java.util.List;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private Game game;
    private String message;
    private List<Integer> targetList;
    private int position_x;
    private int position_y;
    private String targets;
    private List<int[]> combo;
    private boolean isFinished;
    private DropItem randomItem;
}
