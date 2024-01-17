package com.ssafy.puzzlepop.engine;

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
    private Set<Piece> set = new HashSet<>();


    public Piece(int index) {
        this.index = index;
    }


}
