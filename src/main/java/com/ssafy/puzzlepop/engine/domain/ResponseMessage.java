package com.ssafy.puzzlepop.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private Game game;
    private String message;
    private List<Integer> targetList;
    private int position_x;
    private int position_y;
    private String targets;
    private List<Integer> combo;
    private boolean isFinished;
    private DropItem randomItem;
}
