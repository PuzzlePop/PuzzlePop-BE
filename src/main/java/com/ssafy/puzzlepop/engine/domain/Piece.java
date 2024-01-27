package com.ssafy.puzzlepop.engine.domain;

import java.util.*;
import lombok.*;

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


    public Piece(int index) {
        this.index = index;
    }


}
