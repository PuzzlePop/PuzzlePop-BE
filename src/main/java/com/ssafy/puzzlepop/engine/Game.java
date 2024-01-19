package com.ssafy.puzzlepop.engine;

import lombok.NoArgsConstructor;

import java.awt.*;

@NoArgsConstructor
public class Game {
    private PuzzleBoard redPuzzle;
    private PuzzleBoard bluePuzzle;

    private Item[] itemList = new Item[5];
    private int itemCount = 0;


    public void gameStart(Picture p) {
        redPuzzle = new PuzzleBoard();
        bluePuzzle = new PuzzleBoard();

        redPuzzle.init(p);
        bluePuzzle.init(p);
    }

    public void addItem(Item item) {
        if (itemCount > 5) {
            return;
        }

        itemList[itemCount++] = item;
    }

    public void swapItem(int from, int to) {
        Item tmp = itemList[from];
        itemList[from] = itemList[to];
        itemList[to] = tmp;
    }


    public void useItem(int itemNumber, PuzzleBoard puzzle) {
        itemList[itemNumber-1].run(puzzle);
        itemList[itemNumber-1] = null;
        itemCount--;
    }
}
