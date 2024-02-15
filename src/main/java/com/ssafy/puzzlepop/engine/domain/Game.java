package com.ssafy.puzzlepop.engine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter @Setter
@NoArgsConstructor
public class Game {
    private String gameId;
    private String gameName;
    private int roomSize;

    private String gameType;

    private User admin;

    private Picture picture;

    private Team redTeam;
    private Team blueTeam;
    private List<User> players;
    private PuzzleBoard redPuzzle;
    private PuzzleBoard bluePuzzle;

    private Date startTime;
    private Date finishTime;

    private boolean isStarted = false;
    private Map<String, User> sessionToUser;
    private Map<String, DropItem> dropRandomItem;
    private boolean isFinished = false;
    private boolean isSaved = false;

    public void changeTeam(User a, User b) {
        if (redTeam.isIn((a)) && blueTeam.isIn((b))) {
            redTeam.deletePlayer(a);
            blueTeam.addPlayer(a);

            blueTeam.deletePlayer(b);
            redTeam.addPlayer(b);
        } else if (a == null) {
            if (redTeam.isIn(b)) {
                redTeam.deletePlayer(b);
                blueTeam.addPlayer(b);
            } else if (blueTeam.isIn(b)) {
                blueTeam.deletePlayer(b);
                redTeam.addPlayer(b);
            }
        } else if (b == null) {
            if (redTeam.isIn(a)) {
                redTeam.deletePlayer(a);
                blueTeam.addPlayer(a);
            } else if (blueTeam.isIn(a)) {
                blueTeam.deletePlayer(a);
                redTeam.addPlayer(a);
            }
        }

    }

    public void exitPlayer(String sessionId) {
        User user = sessionToUser.get(sessionId);
        if (user == admin) {
            Collection<User> values = sessionToUser.values();
            Iterator<User> iter = values.iterator();
            if (values.size() > 1) {
                for (int i = 0; i < 2; i++) {
                    admin = iter.next();
                }
                System.out.println("방장이 " + admin.getId() + " 님으로 바뀌었습니다~");
            }
        }

        if (redTeam.getPlayers().contains(user)) {
            redTeam.deletePlayer(user);
        } else {
            blueTeam.deletePlayer(user);
        }

        sessionToUser.remove(sessionId);
    }

    public boolean enterPlayer(User user, String sessionId) {
        if (sessionToUser.isEmpty()) {
            admin = user;
        }
        sessionToUser.put(sessionId, user);

        if (gameType.equals("BATTLE")) {
            if (redTeam.getPlayers().contains(user) || blueTeam.getPlayers().contains(user)) {
                return true;
            }

            if (redTeam.getPlayers().size() < roomSize / 2) {
                redTeam.addPlayer(user);
                return true;
            } else {
                if (blueTeam.getPlayers().size() < roomSize / 2) {
                    blueTeam.addPlayer(user);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (redTeam.getPlayers().contains(user)) {
                return true;
            }

            if (redTeam.getPlayers().size() < roomSize) {
                redTeam.addPlayer(user);
                return true;
            } else {
                return false;
            }
        }
    }

    public static Game create(Room room) {
        String name = room.getName();
        String userid = room.getUserid();
        int roomSize = room.getRoomSize();
        String gameType = room.getGameType();

        Map<String, User> map = new HashMap<>();
        Map<String, DropItem> dropItemMap = new HashMap<>();

        Game game = new Game();
        String uuid = UUID.randomUUID().toString();


        game.sessionToUser = map;
        game.dropRandomItem = dropItemMap;





        if (gameType.equals("BATTLE")) {
            Team red = new Team(new LinkedList<>());
            Team blue = new Team(new LinkedList<>());

            game.redTeam = red;
            game.blueTeam = blue;

            game.gameType = "BATTLE";

            game.picture = Picture.create();

            game.gameId = uuid;
            game.gameName = name;
            game.roomSize = roomSize;

            game.startTime = new Date();

            System.out.println(name + "배틀 방 생성 / id = " + uuid);
        } else if (gameType.equals("COOPERATION")){
            Team red = new Team(new LinkedList<>());
            Team blue = new Team(new LinkedList<>());

            game.redTeam = red;
            game.blueTeam = blue;

            game.gameType = "COOPERATION";

            game.picture = Picture.create();

            game.gameId = uuid;
            game.gameName = name;
            game.roomSize = roomSize;

            game.startTime = new Date();

            System.out.println(name + "협동 방 생성 / id = " + uuid);
        }

        return game;
    }

    public boolean isEmpty() {
        if (redTeam.getPlayers().size() + blueTeam.getPlayers().size() == 0) {
            return true;
        }

        return false;
    }


    public synchronized void start() {
        if (isStarted) {
            return;
        }

        redPuzzle = new PuzzleBoard();
        bluePuzzle = new PuzzleBoard();
        redPuzzle.init(picture, gameType);
        bluePuzzle.init(picture, gameType);
        players = new LinkedList<>();
        players.addAll(redTeam.getPlayers());
        players.addAll(blueTeam.getPlayers());

        startTime = new Date();
        isStarted = true;

        System.out.println("------------------게임 시작-------------------");
//        redPuzzle.print();
//        bluePuzzle.print();
    }

    //초 단위 경과 시간
    public long getTime() {
        Date nowTime = new Date();
        long start = startTime.getTime();
        long now = nowTime.getTime();
        return (now-start)/1000;
    }

    public void updatePicture(Picture p) {
        this.picture = p;
    }
}
