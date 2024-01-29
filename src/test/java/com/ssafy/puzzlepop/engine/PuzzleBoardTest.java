package com.ssafy.puzzlepop.engine;

import com.ssafy.puzzlepop.engine.domain.Picture;
import com.ssafy.puzzlepop.engine.domain.Piece;
import com.ssafy.puzzlepop.engine.domain.PuzzleBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;

class PuzzleBoardTest {
    @Test
    @DisplayName("초기화 테스트")
    void initTest() {
        Picture p = new Picture(64, 48, "String");
        PuzzleBoard puzzle = new PuzzleBoard();
        puzzle.init(p);
        Piece[][][] board = puzzle.getBoard();

        for (int i = 0; i < puzzle.getLengthCnt(); i++) {
            for (int j = 0; j < puzzle.getWidthCnt(); j++) {
                System.out.print(Arrays.toString(board[0][i][j].getType()) + " ");
            }
            System.out.println();
        }
    }

    @Test
    @DisplayName("조각 결합 테스트")
    void addTest() {
        Picture p = new Picture(64, 48, "String");
        PuzzleBoard puzzle = new PuzzleBoard();
        puzzle.init(p);

        //피스 0과 4를 합쳐라!
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(4);
        puzzle.addPiece(list);

        //피스 2와 5를 합쳐라
        List<Integer> list2 = new LinkedList<>();
        list2.add(2);
        list2.add(5);
        puzzle.addPiece(list2);

        for (int a : list2) {
            list.add(a);
        }

        puzzle.addPiece(list);

        //피스 0을 붙이다
        list.add(0);
        puzzle.addPiece(list);

        //피스 3과 7을 붙이다
        list2 = new LinkedList<>();
        list2.add(3);
        list2.add(7);

        puzzle.addPiece(list2);


        //[0,1,2,4,5]와 [3,7]을 붙이다
        for (int i = 0; i < list2.size(); i++) {
            list.add(list2.get(i));
        }
        puzzle.addPiece(list);


        for (Set<Piece> set : puzzle.getBundles()) {
            System.out.println("집합");
            for (Piece a : set) {
                System.out.println(a);
            }
        }
    }

    @Test
    @DisplayName("콤보 효과")
    void comboTest() {
        Picture p = new Picture(64, 48, "String");
        PuzzleBoard puzzle = new PuzzleBoard();
        puzzle.init(p);

        //피스 0과 4를 합쳐라!
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(4);
        puzzle.addPiece(list);

        //피스 2와 5를 합쳐라
        List<Integer> list2 = new LinkedList<>();
        list2.add(2);
        list2.add(5);
        puzzle.addPiece(list2);

        for (int a : list2) {
            list.add(a);
        }

        puzzle.addPiece(list);

        //피스 0을 붙이다
        list.add(0);
        puzzle.addPiece(list);

        //피스 3과 7을 붙이다
        list2 = new LinkedList<>();
        list2.add(3);
        list2.add(7);

        puzzle.addPiece(list2);




        //[0,1,2,4,5]와 [3,7]을 붙이다
        for (int i = 0; i < list2.size(); i++) {
            list.add(list2.get(i));
        }
        puzzle.addPiece(list);

        for (Set<Piece> set : puzzle.getBundles()) {
            System.out.println("집합");
            for (Piece a : set) {
                System.out.println(a);
            }
        }

        System.out.println(puzzle.combo(list));
//        puzzle.combo(list);

        for (Set<Piece> set : puzzle.getBundles()) {
            System.out.println("집합");
            for (Piece a : set) {
                System.out.println(a);
            }
        }
    }
}