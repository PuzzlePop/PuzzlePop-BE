package com.ssafy.puzzlepop.engine.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Piece {
    private int index;
    private int correctTopIndex;
    private int correctBottomIndex;
    private int correctLeftIndex;
    private int correctRightIndex;
    private int[] type;
    private double position_x;
    private double position_y;
    private boolean locked = false;


    public Piece(int index) {
        this.index = index;
    }


}
