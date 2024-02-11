package com.ssafy.puzzlepop.engine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

//TODO
//알고리즘 정교화
@Getter
@NoArgsConstructor
public class PuzzleBoard {
    private Picture picture; //퍼즐에 쓰이는 사진
    private HashMap<Integer, int[]> idxToCoordinate; //고유 인덱스에 따른 2차원 배열(board)에서의 좌표 정보

    //0번째는 정답지, 1번쨰는 현재 조각 위치
    private Piece[][] board; //조각들이 들어있는 2차원 배열
    private int pieceSize; //조각 크기
    private int widthCnt; //조각 수에 따른 가로 조각 개수
    private int lengthCnt; //조각 수에 따른 세로 조각 개수
    private List<Set<Piece>> bundles = new LinkedList<>(); //조합된 퍼즐 뭉탱이들
    private boolean[][] isCorrected; //조합된 퍼즐인지 확인하는 2차원 배열
    private int correctedCount; //현재까지 맞춘 개수
    private boolean isCompleted = false;

    //랜덤 타입 적용에 쓰일 인덱스 상수
    private final int TOP = 0;
    private final int RIGHT = 1;
    private final int BOTTOM = 2;
    private final int LEFT = 3;

    private LinkedList<Date> comboTimer = new LinkedList<>();

    private Item[] itemList = new Item[5];
    private int itemCount = 0;
    private boolean[][] visited;

    private final int CANVAS_WIDTH = 1000;
    private final int CANVAS_LENGTH = 750;

    public Item addItem(ItemType type) {
        if (itemCount >= 5) {
            System.out.println("아이템 추가 실패");
            return null;
        }

        Item item = new Item(type);
        for (int i = 0; i < 5; i++) {
            if (itemList[i] == null) {
                itemList[i] = item;
                itemCount++;
                break;
            }
        }
        System.out.println("아이템 추가 성공");
        return item;
    }

    public void swapItem(int from, int to) {
        Item tmp = itemList[from];
        itemList[from] = itemList[to];
        itemList[to] = tmp;
    }

    public List<Integer> useRandomItem(DropItem item, PuzzleBoard puzzle) {
        return item.run(puzzle);
    }

    public List<Integer> useItem(int itemNumber, PuzzleBoard puzzle) {
        if (itemList[itemNumber-1] == null) {
            System.out.println("아이템 사용 실패 : 없음");
            return null;
        }

        System.out.println("아이템 사용 : " + itemList[itemNumber-1]);
        List<Integer> targets = itemList[itemNumber-1].run(puzzle);
        itemList[itemNumber-1] = null;
        itemCount--;

        return targets;
    }


    //퍼즐 판 초기화
    //1. 사진 입력
    //2. 조각 수 판별, 가로 세로 조각수 판별
    //3. 퍼즐 판 초기화 및 고유 인덱스 번호 할당, 동시에 각각의 조각들이 상하좌우에 있는 조각들의 고유 인덱스 번호를 가지고 있음
    //4. 판에 조각들 랜덤 모양으로 할당하기
    public Piece[][] init(Picture p, String gameType) {
        picture = p;
        pieceSize = p.getPieceSize();
        widthCnt = p.getWidthPieceCnt();
        lengthCnt = p.getLengthPieceCnt();

        //퍼즐 조각 초기화
        //고유 인덱스 할당
        //고유 인덱스로 정답 판별할 수 있도록, 상하좌우 주변 퍼즐에 대한 고유 인덱스 정보를 포함하여 초기화
        board = new Piece[lengthCnt][widthCnt];
        isCorrected = new boolean[lengthCnt][widthCnt];
        idxToCoordinate = new HashMap<>();

        boolean[] randomVisited = new boolean[widthCnt * lengthCnt];
        int cnt = 0;
        for (int i = 0; i < lengthCnt; i++) {
            for (int j = 0; j < widthCnt; j++) {
                board[i][j] = new Piece(cnt);

                idxToCoordinate.put(cnt, new int[]{i, j});

                if (cnt+1 >= widthCnt*(i+1)) {
                    board[i][j].setCorrectRightIndex(-1);
                } else {
                    board[i][j].setCorrectRightIndex(cnt+1);
                }

                if (cnt-1 < i*widthCnt) {
                    board[i][j].setCorrectLeftIndex(-1);
                } else {
                    board[i][j].setCorrectLeftIndex(cnt-1);
                }

                if (cnt-widthCnt < 0) {
                    board[i][j].setCorrectTopIndex(-1);
                } else {
                    board[i][j].setCorrectTopIndex(cnt-widthCnt);
                }

                if (cnt+widthCnt >= widthCnt*lengthCnt) {
                    board[i][j].setCorrectBottomIndex(-1);
                } else {
                    board[i][j].setCorrectBottomIndex(cnt+widthCnt);
                }

                cnt++;
            }
        }

        //퍼즐 생성 알고리즘 적용
        //조각의 타입 = new int[4]
        //인덱스 0 : 상, 인덱스 1 : 우, 인덱스 2 : 하, 인덱스 3 : 좌
        //값 0 : 평면, 값 1 : 들어간 형태, 값 2 : 튀어나온 형태
        for (int i = 0; i < lengthCnt; i++) {
            for (int j = 0; j < widthCnt; j++) {
                Piece now = board[i][j];
                int[] type = new int[4];

                //상단 변
                if (i == 0) {
                    //좌상단 꼭짓점
                    if (j == 0) {
                        type[TOP] = 0;
                        type[LEFT] = 0;
                        type[RIGHT] = random(2);
                        type[BOTTOM] = random(2);
                    }
                    //우상단 꼭짓점
                    else if (j == widthCnt-1) {
                        type[TOP] = 0;
                        type[RIGHT] = 0;
                        type[BOTTOM] = random(2);
                        type[LEFT] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                    //그 외 변
                    else {
                        type[TOP] = 0;
                        type[RIGHT] = random(2);
                        type[BOTTOM] = random(2);
                        type[LEFT] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                }
                //하단 변
                else if (i == lengthCnt-1) {
                    //좌하단 꼭짓점
                    if (j == 0) {
                        type[TOP] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                        type[RIGHT] = random(2);
                        type[BOTTOM] = 0;
                        type[LEFT] = 0;
                    }
                    //우하단 꼭짓점
                    else if (j == widthCnt-1) {
                        type[TOP] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                        type[RIGHT] = 0;
                        type[BOTTOM] = 0;
                        type[LEFT] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                    //그 외 변
                    else {
                        type[TOP] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                        type[RIGHT] = random(2);
                        type[BOTTOM] = 0;
                        type[LEFT] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                }

                //꼭짓점을 제외한 좌측 변
                else if (j == 0) {
                    type[TOP] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                    type[RIGHT] = random(2);
                    type[BOTTOM] = random(2);
                    type[LEFT] = 0;
                }

                //꼭짓점을 제외한 우측 변
                else if (j == widthCnt-1) {
                    type[TOP] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                    type[RIGHT] = 0;
                    type[BOTTOM] = random(2);
                    type[LEFT] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                }

                //그 외 가운데 부분
                else {
                    type[TOP] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                    type[RIGHT] = random(2);
                    type[BOTTOM] = random(2);
                    type[LEFT] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                }

                now.setType(type);

                int idx = random(picture.getLengthPieceCnt() * picture.getWidthPieceCnt())-1;
                while (true) {
                    if (randomVisited[idx]) {
                        idx = random(picture.getLengthPieceCnt()*picture.getWidthPieceCnt())-1;
                        continue;
                    } else {
                        randomVisited[idx] = true;
                        break;
                    }
                }

                Piece randomForPiece = board[idxToCoordinate.get(idx)[0]][idxToCoordinate.get(idx)[1]];
                double x =
                        CANVAS_WIDTH/2 -
                                pieceSize/2 +
                                pieceSize * ((j*2) + (i % 2)) -
                                picture.getImgWidth() + 50;
                double y =
                        CANVAS_LENGTH/2 -
                                pieceSize/2 +
                                pieceSize*i -
                                picture.getLength()/2;

                randomForPiece.setPosition_x(x);
                randomForPiece.setPosition_y(y);
            }
        }

        for (int i = 0; i < lengthCnt; i++) {
            for (int j = 0; j < widthCnt; j++) {
                for (int k = 0; k < 4; k++) {
                    if (board[i][j].getType()[k] == 1) {
                        board[i][j].getType()[k] = -1;
                    } else if (board[i][j].getType()[k] == 2) {
                        board[i][j].getType()[k] = 1;
                    }
                }

                //랜덤 아이템 확률 부여
                Random random = new Random();
                int possibility = random.nextInt(100);
                if (possibility <= 10) {
                    if (gameType.equals("COOPERATION")) {
                        board[i][j].setItem(Item.randomCreateForCooperation());
                    } else if (gameType.equals("BATTLE")) {
                        board[i][j].setItem(Item.randomCreateForBattle());
                    }
                }
            }
        }


        correctedCount = 0;
        return board;
    }

    //퍼즐 조각 결합 짜기
    //파라미터 정보(pieceList) : 게임 관련 소켓에서 결합하는 조각들을 하나의 리스트로 만들어서 파라미터로 입력
    public synchronized void addPiece(List<Integer> pieceList) {
        //이번 결합으로 생기는 조각 뭉탱이들
        Set<Piece> set = new HashSet<>();

        //입력받은 조각들 탐색
        for (int i = 0; i < pieceList.size(); i++) {
            //고유 인덱스를 통해 해당 piece 찾기
            int pieceIdx = pieceList.get(i);
            Piece x = board[idxToCoordinate.get(pieceIdx)[0]][idxToCoordinate.get(pieceIdx)[1]];

            //해당 조각이 이미 어느 집합에 소속되어 있다면
            //그 뭉탱이 집합 삭제
            for (int j = bundles.size()-1; j >= 0; j--) {
                if (bundles.get(j).contains(x)) {
                    set.addAll(bundles.get(j));
                    bundles.remove(j);
                }
            }

            //결합됨을 표시
            isCorrected[idxToCoordinate.get(pieceIdx)[0]][idxToCoordinate.get(pieceIdx)[1]] = true;

            if (board[idxToCoordinate.get(pieceIdx)[0]][idxToCoordinate.get(pieceIdx)[1]].getItem() != null) {
                Item item = board[idxToCoordinate.get(pieceIdx)[0]][idxToCoordinate.get(pieceIdx)[1]].getItem();
                board[idxToCoordinate.get(pieceIdx)[0]][idxToCoordinate.get(pieceIdx)[1]].setItem(null);
                addItem(item.getName());
            }

            //이번 결합 뭉탱이에 추가
            set.add(x);
        }

        //뭉탱이들 리스트에 이번 결합을 통해 나온 뭉탱이 추가
        bundles.add(set);
        updatePieceCount();

        if (correctedCount == widthCnt*lengthCnt && bundles.size() == 1) {
            for (int i = 0; i < lengthCnt; i++) {
                for (int j = 0; j < widthCnt; j++) {
                    if (!isCorrected[i][j]) {
                        return;
                    }
                }
            }
            isCompleted = true;
            System.out.println("게임 끝!");
        }
    }

    public void updatePieceCount() {
        int cnt = 0;
        for (Set<Piece> bundle : bundles) {
            cnt += bundle.size();
        }

        correctedCount = cnt;
    }


    //결합된 조각 삭제
    public double[] deletePiece(int targetIdx) {
        int r = idxToCoordinate.get(targetIdx)[0];
        int c = idxToCoordinate.get(targetIdx)[1];
        if (!isCorrected[r][c])
            return null;


        for (Set<Piece> bundle : bundles) {
            for (Iterator<Piece> it = bundle.iterator(); it.hasNext();) {
                Piece p = it.next();
                if (p.getIndex() == targetIdx) {
                    it.remove();
                    isCorrected[r][c] = false;
                    updatePieceCount();
                    return randomArrange(p.getIndex());
                }
            }
        }

        return null;
    }

    public void searchForGroupDisbandment() {
        visited = new boolean[lengthCnt][widthCnt];

        for (int i = 0; i < lengthCnt; i++) {
            for (int j = 0; j < widthCnt; j++) {
                if (isCorrected[i][j] && !visited[i][j]) {
                    visited[i][j] = true;
                    int cnt = dfsForSearch(i, j);

                    if (cnt == 1) {
                        deletePiece(board[i][j].getIndex());
                    }
                }
            }
        }
    }


    int[] dx = {1,-1,0,0};
    int[] dy = {0,0,-1,1};
    public int dfsForSearch(int r, int c) {
        int cnt = 1;

        for (int i = 0; i < 4; i++) {
            int nr = r + dx[i];
            int nc = c + dy[i];

            if (nr >= 0 && nc >= 0 && nr < lengthCnt && nc < widthCnt) {
                if (isCorrected[nr][nc] && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    cnt += dfsForSearch(nr, nc);
                }
            }
        }

        return cnt;
    }


    //콤보 효과 작동
    //파라미터 : 콤보가 터질 조각 뭉탱이
    public synchronized List<int[]> combo(List<Integer> pieceList, int comboCnt) {
        //4방 탐색용
        //상 우 하 좌
        int[] dx = {-1,0,1,0};
        int[] dy = {0,1,0,-1};

        //입력받은 뭉탱이 주변 조각들(콤보 효과로 달라붙을 수 있는 조건을 가진 조각들)

        Map<Integer, int[]> choiceSet = new HashMap<>();
        for (int pieceIdx : pieceList) {
            Piece x = board[idxToCoordinate.get(pieceIdx)[0]][idxToCoordinate.get(pieceIdx)[1]];
            for (Set<Piece> bundle : bundles) {
                if (bundle.contains(x)) {
                    for (Piece p : bundle) {
                        int[] xy = idxToCoordinate.get(p.getIndex());

                        for (int i = 0; i < 4; i++) {
                            int nr = xy[0]+dx[i];
                            int nc = xy[1]+dy[i];

                            if (nr >= 0 && nc >= 0 && nr < lengthCnt && nc < widthCnt) {
                                if (!isCorrected[nr][nc]) {
                                    choiceSet.put(board[nr][nc].getIndex(), new int[] {board[xy[0]][xy[1]].getIndex(), i});
                                }
                            }
                        }
                    }

                }
            }

        }

        if (choiceSet.isEmpty()) {
            return null;
        }

        //중복이 제거되었으므로 list로 변환
        List<Integer> choiceList = new LinkedList<>(choiceSet.keySet());

        if (choiceList.isEmpty()) {
            return null;
        }
        //위에서 찾은 주변 조각들 중에서 콤보 효과 터지는 조각들 랜덤 결정
        List<int[]> comboPieces = new LinkedList<>();
        for (int i = 1; i <= comboCnt; i++) {
            if (choiceList.isEmpty()) {
                break;
            }
            int randomPieceIdx = random(choiceList.size())-1;
            int chosenPiece = choiceList.get(randomPieceIdx);
            choiceList.remove(randomPieceIdx);
            comboPieces.add(new int[] {chosenPiece, choiceSet.get(chosenPiece)[0], choiceSet.get(chosenPiece)[1]});
        }

        //랜덤 결정 했으니, 입력 받은 리스트에 추가
        //addPiece 메서드를 호출하기 위한 작업
        for (int[] pieceIdx : comboPieces) {
            pieceList.add(pieceIdx[0]);
        }


        //랜덤으로 고른 조각들 원래 뭉탱이에 붙이기
        addPiece(pieceList);
        return comboPieces;
    }

    public void print() {
        System.out.println("---------------------------------------");
        System.out.println("총 조각 : " + widthCnt*lengthCnt);
        System.out.println("맞춘 조각 : " + correctedCount);
        System.out.println("진행률 : " + (((double)correctedCount/((double)widthCnt*(double)lengthCnt))*100) + "%");
        System.out.println("퍼즐 판 정보");
//        for (int i = 0; i < lengthCnt; i++) {
//            for (int j = 0; j < widthCnt; j++) {
//                System.out.print(board[i][j].getIndex() + " ");
//            }
//            System.out.println();
//        }

        System.out.println("맞춰진 조각 정보");
        for (int i = 0; i < lengthCnt; i++) {
            for (int j = 0; j < widthCnt; j++) {
                System.out.print(isCorrected[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("조각 뭉탱이들 정보");
        for (Set<Piece> set : bundles) {
            if (set == null)
                continue;
            System.out.println(set);
        }

//        System.out.println("아이템 리스트");
//        System.out.println(Arrays.toString(itemList));
        System.out.println("---------------------------------------");
    }

    public double[] randomArrange(int pieceIdx) {
        int r = idxToCoordinate.get(pieceIdx)[0];
        int c = idxToCoordinate.get(pieceIdx)[1];
        board[r][c].setPosition_x(random(CANVAS_WIDTH));
        board[r][c].setPosition_y(random(CANVAS_LENGTH));
        return new double[]{board[r][c].getPosition_x(), board[r][c].getPosition_y()};
    }

    public int random(int range) {
        return ((int)(Math.random()*range)) + 1;
    }


    public static int GCD(int a, int b) {
        if (b == 0) {
            return a;
        }

        return GCD(b, a%b);
    }


}
