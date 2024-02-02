package com.ssafy.puzzlepop.engine.service;

import com.google.gson.Gson;
import com.ssafy.puzzlepop.engine.InGameMessage;
import com.ssafy.puzzlepop.engine.domain.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Getter
public class GameService {
    private Map<String, Game> gameRooms;
    private Gson gson;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        gameRooms = new LinkedHashMap<>();
        gameRooms = Collections.synchronizedMap(gameRooms);
        gson = new Gson();
    }

    //협동 게임방 불러오기
    public List<Game> findAllCooperationRoom() {
        List<Game> result = new ArrayList<>(gameRooms.values());
        for (int i = result.size()-1; i >= 0; i--) {
            if (!result.get(i).getGameType().equals("COOPERATION")) {
                result.remove(i);
            }
        }

        //최근 생성 순으로 반환
        Collections.reverse(result);

        return result;
    }

    //배틀 게임방 불러오기
    public List<Game> findAllBattleRoom() {
        List<Game> result = new ArrayList<>(gameRooms.values());
        for (int i = result.size()-1; i >= 0; i--) {
            if (!result.get(i).getGameType().equals("BATTLE")) {
                result.remove(i);
            }
        }

        //최근 생성 순으로 반환
        Collections.reverse(result);

        return result;
    }

    public Game findById(String roomId) {
        if (gameRooms.get(roomId) == null) {
            System.out.println("game이 없음.......");
        }
        return gameRooms.get(roomId);
    }

    //채팅방 생성
    public Game createRoom(Room room) {
        Game game = Game.create(room);
        gameRooms.put(game.getGameId(), game);
        return game;
    }

    public void deleteRoom(String name) {
        gameRooms.remove(name);
    }

//    public Game createRoom(String name, String userid, GameType type) {
//        Game game = Game.create(name, userid, type);
//        gameRooms.put(game.getGameId(), game);
//        return game;
//    }

    public Game startGame(String roomId) {
        System.out.println("GameService.startGame");
        System.out.println("gameid = " + roomId);
        System.out.println(gameRooms);
        Game game = findById(roomId);
        game.start();
        return game;
    }

    public ResponseMessage playGame(InGameMessage inGameMessage) {

        String roomId = inGameMessage.getRoomId();
        String sender = inGameMessage.getSender();
        String message = inGameMessage.getMessage();
        String targets = inGameMessage.getTargets();
        int position_x = inGameMessage.getPosition_x();
        int position_y = inGameMessage.getPosition_y();

        System.out.println("GameService.playGame");
        System.out.println(gameRooms);
        ResponseMessage res = new ResponseMessage();
        Game game = findById(roomId);
        //res.setGame(game);

        System.out.println("sender = " + sender);
        System.out.println("targets = " + targets);

        PuzzleBoard ourPuzzle;
        PuzzleBoard yourPuzzle;
        if (game.getRedTeam().isIn(sender)) {
            System.out.println("얘 레드팀임");
            ourPuzzle = game.getRedPuzzle();
            yourPuzzle = game.getBluePuzzle();
        } else if (game.getBlueTeam().isIn(sender)){
            System.out.println("얘 블루팀임");
            ourPuzzle = game.getBluePuzzle();
            yourPuzzle = game.getRedPuzzle();
        } else {
            System.out.println("팀이 없는데, 이거 맞아?");
            res.setMessage("팀 없는데?");
            return res;
        }
        //TODO
        //게임 진행 로직

        //TODO
        // 배틀 형태로도 구현해야함
        if (message.equals("ADD_PIECE")) {
            String[] stringToInt = targets.split(",");
            List<Integer> pieces = new LinkedList<>();
            for (int i = 0; i < stringToInt.length; i++) {
                pieces.add(Integer.parseInt(stringToInt[i]));
            }
            System.out.println("ADD_PIECE");

            game.getRedPuzzle().addPiece(pieces);
            game.getRedPuzzle().print();

            int comboCnt = comboCheck(game.getRedPuzzle());
            if (comboCnt != 0) {
                List<Integer> comboPieces = game.getRedPuzzle().combo(pieces, comboCnt);
                System.out.println("콤보 대상 : " + comboPieces);
                res.setMessage("COMBO");
                res.setTargetList(comboPieces);
            }
        } else if (message.equals("USE_ITEM")) {
            Item item = ourPuzzle.getItemList()[Integer.parseInt(targets)];
            ItemType type = item.getName();

            if (type == ItemType.FIRE || type == ItemType.EARTHQUAKE || type == ItemType.ROCKET) {
                Item[] yourItemList = yourPuzzle.getItemList();
                int shield = -1;
                int mirror = -1;
                for (int i = 0; i < 5; i++) {
                    if (yourItemList[i].getName() == ItemType.MIRROR) {
                        mirror = i;
                    } else if (yourItemList[i].getName() == ItemType.SHIELD) {
                        shield = i;
                    }
                }

                ourPuzzle.useItem(Integer.parseInt(targets), ourPuzzle);
                //둘다 없을 때
                if (mirror != -1 && shield != -1) {
                    item.run(yourPuzzle);
                }
                //반사됨
                else if (mirror != -1 && shield == -1) {
                    item.run(ourPuzzle);
                }
                //방어됨
                else if (mirror == -1 && shield != -1) {
                    //아무일 없음
                }
                //둘다 있을 때
                else {
                    //반사부터 적용됨
                    item.run(ourPuzzle);
                }
            } else if (type == ItemType.HINT || type == ItemType.FRAME || type == ItemType.MAGNET) {
                ourPuzzle.useItem(Integer.parseInt(targets), ourPuzzle);
            } else {

            }
            game.getRedPuzzle().useItem(Integer.parseInt(targets), game.getRedPuzzle());

        } else if (message.equals("MOUSE_DOWN")) {
            PieceDto[] arr = gson.fromJson(targets, PieceDto[].class);

            for (int i = 0; i < arr.length; i++) {
                PieceDto now = arr[i];
                int[] p = ourPuzzle.getIdxToCoordinate().get(now.getIndex());
                if (ourPuzzle.getBoard()[0][p[0]][p[1]].isLocked()) {
                    res.setMessage("BLOCKED");
                    return res;
                }

                ourPuzzle.getBoard()[0][p[0]][p[1]].setLocked(true);
            }

            System.out.println(targets + " 피스 잠금");
            res.setMessage("LOCKED");
            res.setTargets(targets);
        } else if (message.equals("MOUSE_UP")) {
            PieceDto[] arr = gson.fromJson(targets, PieceDto[].class);

            for (int i = 0; i < arr.length; i++) {
                PieceDto now = arr[i];
                int[] p = ourPuzzle.getIdxToCoordinate().get(now.getIndex());

                ourPuzzle.getBoard()[0][p[0]][p[1]].setLocked(false);
            }

            System.out.println(targets + " 피스 잠금 해제");
            res.setMessage("UNLOCKED");
            res.setTargets(targets);
        } else if (message.equals("MOUSE_DRAG")) {
            res.setMessage("MOVE");

            PieceDto[] arr = gson.fromJson(targets, PieceDto[].class);

            for (int i = 0; i < arr.length; i++) {
                PieceDto now = arr[i];
                int[] p = ourPuzzle.getIdxToCoordinate().get(now.getIndex());

                ourPuzzle.getBoard()[0][p[0]][p[1]].setPosition_x(now.getX());
                ourPuzzle.getBoard()[0][p[0]][p[1]].setPosition_y(now.getY());
            }

            res.setTargets(targets);
        } else {
            System.out.println("구현중인 명령어 : " + message);
            System.out.println("targets = " + targets);
        }

        return res;
    }

    public boolean enterGame(String gameId, String userId, String sessionId) {
        Game game = gameRooms.get(gameId);
        if (game.enterPlayer(new User(userId), sessionId)) {
            return true;
        }

        return false;
    }

    public int comboCheck(PuzzleBoard puzzle) {
        Date now = new Date();
        if (puzzle.getComboTimer().isEmpty()) {
            puzzle.getComboTimer().add(now);
        } else {
            if (now.getTime()/1000 - puzzle.getComboTimer().peekLast().getTime()/1000 <= 5) {
                puzzle.getComboTimer().add(now);
            } else {
                puzzle.getComboTimer().clear();
                puzzle.getComboTimer().add(now);
            }
        }

        if (puzzle.getComboTimer().size() % 3 == 0) {
            return puzzle.getComboTimer().size()/3;
        } else {
            return 0;
        }
    }
}
