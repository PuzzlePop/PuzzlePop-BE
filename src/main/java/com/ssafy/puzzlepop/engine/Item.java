package com.ssafy.puzzlepop.engine;

import java.util.LinkedList;
import java.util.List;

public class Item {
    private Long id;
    private String name;
    private String description;
    private String img_path;

    public Item (Long id) {
        this.id = id;
        if (id == 1) {
            this.name = "힌트";
            this.description = "이 아이템을 클릭하면, 아직 맞추지 않은 2조각을 맞출 수 있어요!";
            this.img_path = "path";
        } else if (id == 2) {
            this.name = "지진";
            this.description = "이 아이템을 클릭하면, 상대방의 아직 맞추지 않은 퍼즐들이 섞여요!";
            this.img_path = "path";
        } else if (id == 3) {
            this.name = "거울";
        } else if (id == 4) {
            this.name = "액자";
        } else if (id == 5) {
            this.name = "쉴드";
        } else if (id == 6) {
            this.name = "자석";
        } else if (id == 7) {
            this.name = "주사위";
        } else if (id == 8) {
            this.name = "로켓";
        } else if (id == 9) {
            this.name = "불지르기";
        } else if (id == 10) {

        }
    }

    public void run(PuzzleBoard puzzle) {
        switch (this.id.intValue()) {
            case 1:
                System.out.println(puzzle + "에 힌트 아이템 효과 발동~");
                boolean[][] tmp = puzzle.getIsCorrected();
                for (int i = 0; i < puzzle.getLengthCnt(); i++) {
                    for (int j = 0; j < puzzle.getWidthCnt()-1; j++) {
                        if (!tmp[i][j] && !tmp[i][j+1]) {
                            System.out.println(puzzle.getBoard()[0][i][j] + " " + puzzle.getBoard()[0][i][j+1]);
                            return;
                        }
                    }
                }
                break;

            case 2:
                System.out.println(puzzle + "에 지진 아이템 효과 발동~");
                puzzle.randomArrange();
                break;

            case 3:
                //게임 내에서 구현
                break;

            //액자
            case 4:
                List<Integer> list = new LinkedList<>();
                for (int i = 0; i < puzzle.getLengthCnt(); i++) {
                    if (!puzzle.getIsCorrected()[i][0]) {
                        list.add(puzzle.getBoard()[0][i][0].getIndex());
                    }
                }

                for (int i = 0; i < puzzle.getWidthCnt(); i++) {
                    if (!puzzle.getIsCorrected()[0][i]) {
                        list.add(puzzle.getBoard()[0][0][i].getIndex());
                    }
                }

                for (int i = 0; i < puzzle.getLengthCnt(); i++) {
                    if (!puzzle.getIsCorrected()[i][puzzle.getWidthCnt()-1]) {
                        list.add(puzzle.getBoard()[0][i][puzzle.getWidthCnt()-1].getIndex());
                    }
                }

                for (int i = 0; i < puzzle.getWidthCnt(); i++) {
                    if (!puzzle.getIsCorrected()[puzzle.getLengthCnt()-1][i]) {
                        list.add(puzzle.getBoard()[0][puzzle.getLengthCnt()-1][i].getIndex());
                    }
                }

                puzzle.addPiece(list);
                break;

            case 5:
                //게임 내에서 구현
                break;

            //자석
            case 6:
                List<Integer> targets = new LinkedList<>();
                for (int i = 0; i < puzzle.getLengthCnt(); i++) {
                    for (int j = 0; j < puzzle.getWidthCnt(); j++) {
                        if (!puzzle.getIsCorrected()[i][j]) {
                            targets.add(puzzle.getBoard()[0][i][j].getIndex());

                            int bottom = puzzle.getBoard()[0][i][j].getCorrectBottomIndex();
                            if (bottom != -1) {
                                int[] coor = puzzle.getIdxToCoordinate().get(bottom);
                                if (!puzzle.getIsCorrected()[coor[0]][coor[1]]) {
                                    targets.add(bottom);
                                }
                            }

                            int top = puzzle.getBoard()[0][i][j].getCorrectTopIndex();
                            if (top != -1) {
                                int[] coor = puzzle.getIdxToCoordinate().get(top);
                                if (!puzzle.getIsCorrected()[coor[0]][coor[1]]) {
                                    targets.add(top);
                                }
                            }

                            int left = puzzle.getBoard()[0][i][j].getCorrectLeftIndex();
                            if (left != -1) {
                                int[] coor = puzzle.getIdxToCoordinate().get(left);
                                if (!puzzle.getIsCorrected()[coor[0]][coor[1]]) {
                                    targets.add(left);
                                }
                            }

                            int right = puzzle.getBoard()[0][i][j].getCorrectRightIndex();
                            if (right != -1) {
                                int[] coor = puzzle.getIdxToCoordinate().get(right);
                                if (!puzzle.getIsCorrected()[coor[0]][coor[1]]) {
                                    targets.add(right);
                                }
                            }

                            puzzle.addPiece(targets);
                            return;
                        }
                    }
                }

                break;

            //불지르기
            case 9:

                break;
        }
    }
}
