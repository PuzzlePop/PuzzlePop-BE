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
        String ourColor;
        PuzzleBoard yourPuzzle;
        String yourColor;

        List<Integer> targetsList;


        if (game.getRedTeam().isIn(sender)) {
            System.out.println("얘 레드팀임");
            ourPuzzle = game.getRedPuzzle();
            ourColor = "RED";
            yourPuzzle = game.getBluePuzzle();
            yourColor = "BLUE";
        } else if (game.getBlueTeam().isIn(sender)){
            System.out.println("얘 블루팀임");
            ourPuzzle = game.getBluePuzzle();
            ourColor = "BLUE";
            yourPuzzle = game.getRedPuzzle();
            yourColor = "RED";
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

            ourPuzzle.addPiece(pieces);
            ourPuzzle.print();

            res.setMessage("ADD_PIECE");
            res.setTargets(targets);
            int comboCnt = comboCheck(ourPuzzle);
            if (comboCnt != 0) {
                List<int[]> comboPieces = ourPuzzle.combo(pieces, comboCnt);
                if (comboPieces == null) {
                    return res;
                }
                for (int[] comboSet : comboPieces) {
                    System.out.print(Arrays.toString(comboSet) + " | ");
                }
                res.setCombo(comboPieces);
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


                //둘다 없을 때
                if (mirror != -1 && shield != -1) {
                    res.setMessage("ATTACKED");
                    res.setTargetList(ourPuzzle.useItem(Integer.parseInt(targets), yourPuzzle));
                }
                //반사됨
                else if (mirror != -1 && shield == -1) {
                    res.setMessage("MIRROR");
                    res.setTargetList(ourPuzzle.useItem(Integer.parseInt(targets), ourPuzzle));
                }
                //방어됨
                else if (mirror == -1 && shield != -1) {
                    //아무일 없음
                    res.setMessage("SHIELD");
                }
                //둘다 있을 때
                else {
                    //반사부터 적용됨
                    res.setMessage("MIRROR");
                    res.setTargetList(ourPuzzle.useItem(Integer.parseInt(targets), ourPuzzle));
                }
            } else if (type == ItemType.HINT || type == ItemType.FRAME || type == ItemType.MAGNET) {
                res.setMessage(String.valueOf(type));
                res.setTargetList(ourPuzzle.useItem(Integer.parseInt(targets), ourPuzzle));
            }


        } else if (message.equals("USE_RANDOM_ITEM")) {
            DropItem item = game.getDropRandomItem().get(targets);
            game.getDropRandomItem().remove(targets);

            targetsList = ourPuzzle.useRandomItem(item, yourPuzzle);

            res.setMessage("USE_RANDOM_ITEM");
            res.setTargets(yourColor);
            res.setTargetList(targetsList);
        } else if (message.equals("MOUSE_DOWN")) {
            PieceDto[] arr = gson.fromJson(targets, PieceDto[].class);

            for (int i = 0; i < arr.length; i++) {
                PieceDto now = arr[i];
                int[] p = ourPuzzle.getIdxToCoordinate().get(now.getIndex());
                if (ourPuzzle.getBoard()[p[0]][p[1]].isLocked()) {
                    res.setMessage("BLOCKED");
                    return res;
                }

                ourPuzzle.getBoard()[p[0]][p[1]].setLocked(true);
            }

            System.out.println(targets + " 피스 잠금");
            res.setMessage("LOCKED");
            res.setTargets(targets);
        } else if (message.equals("MOUSE_UP")) {
            PieceDto[] arr = gson.fromJson(targets, PieceDto[].class);

            for (int i = 0; i < arr.length; i++) {
                PieceDto now = arr[i];
                int[] p = ourPuzzle.getIdxToCoordinate().get(now.getIndex());

                ourPuzzle.getBoard()[p[0]][p[1]].setLocked(false);
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

                ourPuzzle.getBoard()[p[0]][p[1]].setPosition_x(now.getX());
                ourPuzzle.getBoard()[p[0]][p[1]].setPosition_y(now.getY());
            }

            res.setTargets(targets);
        } else if (message.equals("ADD_ITEM")) {
            res.setMessage("GET_ITEM");

            ourPuzzle.addItem(ItemType.valueOf(targets));
        } else {
            System.out.println("구현중인 명령어 : " + message);
            System.out.println("targets = " + targets);
        }

        //게임 끝났는지 마지막에 확인
        //TODO
        //끝났을 때 게임 인포에 저장해야함.
        if (game.getGameType().equals("BATTLE")) {
            if (ourPuzzle.isCompleted() || yourPuzzle.isCompleted()) {
                res.setFinished(true);
            }
        } else if (game.getGameType().equals("COOPERATION")) {
            if (ourPuzzle.isCompleted()) {
                res.setFinished(true);
            }
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
