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
    private int comboCnt;
    private boolean isFinished;
    private Item item;
    private DropItem randomItem;
    private double redProgressPercent = -1;
    private double blueProgressPercent = -1;
}
