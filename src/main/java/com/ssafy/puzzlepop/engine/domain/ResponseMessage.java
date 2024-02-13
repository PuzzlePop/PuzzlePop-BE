package com.ssafy.puzzlepop.engine.domain;

import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private Game game;
    private String senderId;
    private String message;
    private String team;
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
    private Map<Integer, double[]> deleted;
    private Item[] redItemList;
    private Item[] blueItemList;
    private List<Set<Piece>> redBundles;
    private List<Set<Piece>> blueBundles;
}
