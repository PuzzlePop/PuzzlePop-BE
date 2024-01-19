package com.ssafy.puzzlepop.engine;

public class Item {
    private Long id;
    private String name;
    private String description;
    private String img_path;

    public Item (Long id) {
        this.id = id;
        if (id == 1) {
          this.name = "힌트";
          this.description = "이 아이템을 클릭하고 퍼즐 조각을 클릭하면, 그 조각 주변의 조각들을 알 수 있어요!";
          this.img_path = "path";
        } else if (id == 2) {

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
        }
    }
}
