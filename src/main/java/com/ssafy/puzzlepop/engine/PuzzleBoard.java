package com.ssafy.puzzlepop.engine;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@NoArgsConstructor
public class PuzzleBoard {
    private Picture picture; //퍼즐에 쓰이는 사진
    private HashMap<Integer, int[]> coordinate; //고유 인덱스에 따른 2차원 배열(board)에서의 좌표 정보
    private Piece[][] board; //조각들이 들어있는 2차원 배열
    private int pieceSize; //조각 수
    private int widthCnt; //조각 수에 따른 가로 조각 개수
    private int lengthCnt; //조각 수에 따른 세로 조각 개수
    private List<Set<Piece>> bundles = new LinkedList<>(); //조합된 퍼즐 뭉탱이들
    private boolean[][] isCorrected; //조합된 퍼즐인지 확인하는 2차원 배열


    //퍼즐 판 초기화
    //1. 사진 입력
    //2. 조각 수 판별, 가로 세로 조각수 판별
    //3. 퍼즐 판 초기화 및 고유 인덱스 번호 할당, 동시에 각각의 조각들이 상하좌우에 있는 조각들의 고유 인덱스 번호를 가지고 있음
    //4. 판에 조각들 랜덤 모양으로 할당하기
    public Piece[][] init(Picture p) {
        picture = p;

        //TODO
        //1. 사진 비율에 따른 여러가지 피스 제공
        //2. 소수인 사이즈를 가진 사진이 들어왔을 때
        pieceSize = GCD(picture.getWidth(), picture.getLength());
        widthCnt = picture.getWidth()/pieceSize;
        lengthCnt = picture.getLength()/pieceSize;

        //퍼즐 조각 초기화
        //고유 인덱스 할당
        //고유 인덱스로 정답 판별할 수 있도록, 상하좌우 주변 퍼즐에 대한 고유 인덱스 정보를 포함하여 초기화
        board = new Piece[lengthCnt][widthCnt];
        isCorrected = new boolean[lengthCnt][widthCnt];
        coordinate = new HashMap<>();

        int cnt = 0;
        for (int i = 0; i < lengthCnt; i++) {
            for (int j = 0; j < widthCnt; j++) {
                board[i][j] = new Piece(cnt);

                coordinate.put(cnt, new int[]{i, j});

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
                        //기본 값
                        type[0] = 0;
                        type[3] = 0;

                        //랜덤값
                        type[1] = random(2);
                        type[2] = random(2);
                    }
                    //우상단 꼭짓점
                    else if (j == widthCnt-1) {
                        type[0] = 0;
                        type[1] = 0;

                        type[2] = random(2);
                        type[3] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                    //그 외 변
                    else {
                        type[0] = 0;

                        type[1] = random(2);
                        type[2] = random(2);
                        type[3] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                }
                //하단 변
                else if (i == lengthCnt-1) {
                    //좌하단 꼭짓점
                    if (j == 0) {
                        //기본 값
                        type[2] = 0;
                        type[3] = 0;
                        type[0] = board[i-1][j].getType()[2] == 2 ? 1 : 2;

                        type[1] = random(2);
                    }
                    //우하단 꼭짓점
                    else if (j == widthCnt-1) {
                        type[0] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                        type[1] = 0;
                        type[2] = 0;
                        type[3] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                    //그 외 변
                    else {
                        type[0] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                        type[1] = random(2);
                        type[2] = 0;
                        type[3] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                    }
                }

                //꼭짓점을 제외한 좌측 변
                else if (j == 0) {
                    type[0] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                    type[1] = random(2);
                    type[2] = random(2);
                    type[3] = 0;
                }

                //꼭짓점을 제외한 우측 변
                else if (j == widthCnt-1) {
                    type[0] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                    type[1] = 0;
                    type[2] = random(2);
                    type[3] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                }

                //그 외 가운데 부분
                else {
                    type[0] = board[i-1][j].getType()[2] == 2 ? 1 : 2;
                    type[1] = random(2);
                    type[2] = random(2);
                    type[3] = board[i][j-1].getType()[1] == 2 ? 1 : 2;
                }

                now.setType(type);
            }
        }

        return board;
    }

    //퍼즐 조각 결합 짜기
    //파라미터 정보(pieceList) : 게임 관련 소켓에서 결합하는 조각들을 하나의 리스트로 만들어서 파라미터로 입력
    public void addPiece(List<Integer> pieceList) {
        //이번 결합으로 생기는 조각 뭉탱이들
        Set<Piece> set = new HashSet<>();

        //입력받은 조각들 탐색
        for (int i = 0; i < pieceList.size(); i++) {
            //고유 인덱스를 통해 해당 piece 찾기
            int pieceIdx = pieceList.get(i);
            Piece x = board[coordinate.get(pieceIdx)[0]][coordinate.get(pieceIdx)[1]];

            //해당 조각이 이미 어느 집합에 소속되어 있다면
            //그 뭉탱이 집합 삭제
            for (int j = bundles.size()-1; j >= 0; j--) {
                if (bundles.get(j).contains(x)) {
                    bundles.remove(j);
                }
            }

            //결합됨을 표시
            isCorrected[coordinate.get(pieceIdx)[0]][coordinate.get(pieceIdx)[1]] = true;
            //이번 결합 뭉탱이에 추가
            set.add(x);
        }

        //뭉탱이들 리스트에 이번 결합을 통해 나온 뭉탱이 추가
        bundles.add(set);
    }


    //콤보 효과 작동
    //파라미터 : 콤보가 터질 조각 뭉탱이
    public List<Integer> combo(List<Integer> pieceList) {
        //4방 탐색용
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,-1,1};

        //입력받은 뭉탱이 주변 조각들(콤보 효과로 달라붙을 수 있는 조건을 가진 조각들)
        Set<Integer> choiceSet = new HashSet<>();
        for (int pieceIdx : pieceList) {
            int[] xy = coordinate.get(pieceIdx);

            for (int i = 0; i < 4; i++) {
                int nr = xy[0]+dx[i];
                int nc = xy[1]+dy[i];

                if (nr >= 0 && nc >= 0 && nr < lengthCnt && nc < widthCnt) {
                    if (!isCorrected[nr][nc]) {
                        choiceSet.add(board[nr][nc].getIndex());
                    }
                }
            }
        }

        //중복이 제거되었으므로 list로 변환
        List<Integer> choiceList = new LinkedList<>(choiceSet);

        //위에서 찾은 주변 조각들 중에서 콤보 효과 터지는 조각들 랜덤 결정
        List<Integer> comboPieces = new LinkedList<>();
        for (int i = 1; i <= pieceList.size()/3; i++) {
            int randomPieceIdx = random(choiceList.size())-1;
            int chosenPiece = choiceList.get(randomPieceIdx);
            choiceList.remove(randomPieceIdx);
            comboPieces.add(chosenPiece);
        }

        //랜덤 결정 했으니, 입력 받은 리스트에 추가
        //addPiece 메서드를 호출하기 위한 작업
        for (int pieceIdx : comboPieces) {
            pieceList.add(pieceIdx);
        }

        //랜덤으로 고른 조각들 원래 뭉탱이에 붙이기
        addPiece(pieceList);
        return comboPieces;
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
