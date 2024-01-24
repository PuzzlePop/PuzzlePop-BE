package com.ssafy.puzzlepop.engine;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
public class Game {
    private Team redTeam;
    private Team blueTeam;
    private PuzzleBoard redPuzzle;
    private PuzzleBoard bluePuzzle;

    private String gameId;
    private String gameName;

    public static Game create(String name) {
        Game game = new Game();
        String uuid = UUID.randomUUID().toString();
        game.gameId = uuid;
        game.gameName = name;

        System.out.println(name + "방 생성 / id = " + uuid);
        return game;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Picture picture = new Picture(64, 48, ".");

        Team redTeam = new Team(new LinkedList<>());
        Team blueTeam = new Team(new LinkedList<>());
        PuzzleBoard redPuzzle = null;
        PuzzleBoard bluePuzzle = null;
        Map<Integer, String> team = new HashMap<>();
        boolean gameStart = false;

        while (true) {
            System.out.print("enter command : ");
            String command = sc.next();

            if (command.equals("adduser")) {
                System.out.print("enter user id : ");
                int id = sc.nextInt();
                System.out.print("enter team : ");
                String color = sc.next();

                if (color.equals("red")) {
                    redTeam.getPlayers().add(new User(id));
                    team.put(id, "red");
                } else {
                    blueTeam.getPlayers().add(new User(id));
                    team.put(id, "blue");
                }

                System.out.println("redTeam = " + redTeam);
                System.out.println("blueTeam = " + blueTeam);
            }

            if (command.equals("gamestart")) {
                redPuzzle = new PuzzleBoard();
                bluePuzzle = new PuzzleBoard();
                redPuzzle.init(picture);
                bluePuzzle.init(picture);
                gameStart = true;

                System.out.println("---------Game Start!!!----------");
                redPuzzle.print();
                bluePuzzle.print();
                continue;
            }

            if (command.equals("addpiece")) {
                System.out.print("enter user id : ");
                int id = sc.nextInt();

                List<Integer> targets = new LinkedList<>();

                while (true) {
                    int piece = sc.nextInt();
                    if (piece == -1) {
                        break;
                    }

                    targets.add(piece);
                }

                if (team.get(id).equals("red")) {
                    redPuzzle.addPiece(targets);
                    if (targets.size() >= 3) {
                        redPuzzle.combo(targets);
                    }
                } else {
                    bluePuzzle.addPiece(targets);
                    if (targets.size() >= 3) {
                        bluePuzzle.combo(targets);
                    }
                }

                redPuzzle.print();
                bluePuzzle.print();

                continue;
            }

            if (command.equals("additem")) {
                System.out.print("enter user id : ");
                int id = sc.nextInt();
                System.out.print("enter item name : ");
                String item = sc.next();

                if (team.get(id).equals("red")) {
                    redPuzzle.addItem(item);
                } else {
                    bluePuzzle.addItem(item);
                }

                redPuzzle.print();
                bluePuzzle.print();

                continue;
            }

            if (command.equals("useitem")) {
                System.out.print("enter user id : ");
                int id = sc.nextInt();
                System.out.print("enter item keyboard number : ");
                int itemNumber = sc.nextInt();

                if (team.get(id).equals("red")) {
                    redPuzzle.useItem(itemNumber, redPuzzle);

                } else {
                    bluePuzzle.useItem(itemNumber, bluePuzzle);
                }

                redPuzzle.print();
                bluePuzzle.print();
                continue;
            }
        }
    }
}
