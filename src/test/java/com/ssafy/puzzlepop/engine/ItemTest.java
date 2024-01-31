package com.ssafy.puzzlepop.engine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class ItemTest {
    @Test
    @DisplayName("아이템 테스트")
    void itemTest() {
        Picture p = new Picture(64, 48, "String");
//
//        Game game = new Game();
//        game.gameStart(p);
        PuzzleBoard redPuzzle = new PuzzleBoard();
        PuzzleBoard bluePuzzle = new PuzzleBoard();

        redPuzzle.init(p);
        bluePuzzle.init(p);
//
//        redPuzzle.print();
//        bluePuzzle.print();


        //피스 0과 4를 합쳐라!
        List<Integer> list = new LinkedList<>();
        list.add(0);
        list.add(4);
        redPuzzle.addPiece(list);

        //피스 1와 5를 합쳐라
        List<Integer> list2 = new LinkedList<>();
        list2.add(1);
        list2.add(5);
        redPuzzle.addPiece(list2);

        //04 와 15를 합쳐라!
        for (int a : list2) {
            list.add(a);
        }
        redPuzzle.addPiece(list);
        redPuzzle.combo(list);

        //피스 3과 7을 붙이다
        list2 = new LinkedList<>();
        list2.add(3);
        list2.add(7);

        bluePuzzle.addPiece(list2);

        //피스 37에 2를 붙이다
        list2.add(2);
        bluePuzzle.addPiece(list2);
        bluePuzzle.combo(list2);

        System.out.println("redPuzzle");
        redPuzzle.print();
        System.out.println("bluePuzzle");
        bluePuzzle.print();

//        redPuzzle.addItem(new Item(Long.parseLong(String.valueOf(1))));
//        redPuzzle.addItem(new Item(Long.parseLong(String.valueOf(2))));
//        redPuzzle.addItem(new Item(Long.parseLong(String.valueOf(1))));
//        redPuzzle.useItem(1, redPuzzle);
//        redPuzzle.useItem(2, bluePuzzle);
//        redPuzzle.useItem(3, redPuzzle);
//
//        System.out.println("redPuzzle");
//        redPuzzle.print();
//
//        bluePuzzle.addItem(new Item(Long.parseLong(String.valueOf(4))));
//        bluePuzzle.useItem(1, bluePuzzle);
//        System.out.println("bluePuzzle 액자 아이템 사용");
//        bluePuzzle.print();
//
//        bluePuzzle.addItem(new Item(Long.parseLong(String.valueOf(6))));
//        bluePuzzle.useItem(1, bluePuzzle);
//
//        bluePuzzle.print();
    }

    @Test
    @DisplayName("자석 테스트")
    void magnetTest() {
//        Picture p = new Picture(64, 48, "String");
//
//        PuzzleBoard redPuzzle = new PuzzleBoard();
//        PuzzleBoard bluePuzzle = new PuzzleBoard();
//
//        redPuzzle.init(p);
//        bluePuzzle.init(p);
//
//        bluePuzzle.addItem(new Item(Long.parseLong(String.valueOf(6))));
//        bluePuzzle.useItem(1, bluePuzzle);
//
//        bluePuzzle.print();
    }

    @Test
    @DisplayName("조각 해체 테스트")
    void deleteTest() {
//        Picture p = new Picture(64, 48, "String");
//
//        PuzzleBoard redPuzzle = new PuzzleBoard();
//        PuzzleBoard bluePuzzle = new PuzzleBoard();
//
//        redPuzzle.init(p);
//        bluePuzzle.init(p);
//
//        bluePuzzle.addItem(new Item(Long.parseLong(String.valueOf(6))));
//        bluePuzzle.useItem(1, bluePuzzle);
//
//
//        bluePuzzle.deletePiece(1);
//        bluePuzzle.print();
    }

//    @Test
//    @DisplayName("불 지르기 테스트")
//    void fireTest() {
//        Picture p = new Picture(64, 48, "String");
//
//        PuzzleBoard redPuzzle = new PuzzleBoard();
//        PuzzleBoard bluePuzzle = new PuzzleBoard();
//
//        redPuzzle.init(p);
//        bluePuzzle.init(p);
//
//        bluePuzzle.print();
//
//        bluePuzzle.addItem("액자");
//        bluePuzzle.useItem(1, bluePuzzle);
//
//        //불지르기
//        redPuzzle.addItem("불지르기");
//        redPuzzle.useItem(1, bluePuzzle);
//
//        bluePuzzle.print();
//    }

    @Test
    @DisplayName("로켓 테스트")
    void rocketTest() {
        Picture p = new Picture(64, 48, "String");

        PuzzleBoard redPuzzle = new PuzzleBoard();
        PuzzleBoard bluePuzzle = new PuzzleBoard();

        redPuzzle.init(p);
        bluePuzzle.init(p);



        bluePuzzle.addItem("frame");
        bluePuzzle.useItem(1, bluePuzzle);
        bluePuzzle.print();

        //로켓
        redPuzzle.addItem("rocket");
        redPuzzle.useItem(1, bluePuzzle);

        bluePuzzle.print();
    }
}
