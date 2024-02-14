package com.ssafy.puzzlepop.engine.controller;

import com.ssafy.puzzlepop.engine.domain.*;
import com.ssafy.puzzlepop.engine.service.GameService;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.exception.ImageException;
import com.ssafy.puzzlepop.image.service.ImageService;
import com.ssafy.puzzlepop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
@CrossOrigin("*")
public class GameRoomController {

    private final ImageService imageService;
    private final GameService gameService;
    private final UserService userService;

    //협동 게임 방 리스트
    @GetMapping("/rooms/cooperation")
    @ResponseBody
    public ResponseEntity<?> cooperationRooms() {
        List<Game> allCooperationRoom = gameService.findAllCooperationRoom();
        return ResponseEntity.ok(allCooperationRoom);
    }

    //배틀 게임 방 리스트
    @GetMapping("/rooms/battle")
    @ResponseBody
    public ResponseEntity<?> battleRooms() {
        List<Game> allBattleRoom = gameService.findAllBattleRoom();
        return ResponseEntity.ok(allBattleRoom);
    }

    //게임 생성
    @PostMapping("/room")
    @ResponseBody
    public Game createRoom(@RequestBody Room room) {
        Game game = gameService.createRoom(room);
        System.out.println(game.getGameId() + "생성됨");
        return game;
    }


    @PostMapping("/room/picture")
    @ResponseBody
    public ResponseEntity<?> updatePicture(@RequestBody RequestContainerDto requestContainerDto) throws Exception {
        Picture p = requestContainerDto.getPicture();
        User user = requestContainerDto.getUser();
        String gameId = requestContainerDto.getUuid();

        if (gameService.findById(gameId) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게임 없는데 너 뭐냐?");
        }

        ImageDto imageDtoById = imageService.getImageDtoById(p.getId());
        String filepath = imageDtoById.getFilepath();
        String filenameExtension = imageDtoById.getFilenameExtension();

        File file = new File(filepath+"."+filenameExtension);
        BufferedImage image = ImageIO.read(file);
        String base64Image = encodeImageToBase64(image, filenameExtension);

        int width = image.getWidth();
        int height = image.getHeight();
        p.create(width, height, filepath, 40, base64Image);

        Game game = gameService.findById(gameId);
        game.updatePicture(p);
        System.out.println(p);
        return ResponseEntity.ok(p);
    }

    public static String encodeImageToBase64(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }



    //게임 대기실 입장
    @PostMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<?> roomInfo(@PathVariable String roomId, @RequestBody User user) {
        Game game = gameService.findById(roomId);
//
//        //게임 입장했을때 임시로 여기서 저장
//        System.out.println(user);
//        com.ssafy.puzzlepop.user.domain.UserDto dbUser = new com.ssafy.puzzlepop.user.domain.UserDto();
//        dbUser.setPlayingGameID(Integer.valueOf(game.getGameId().substring(0,1)));
//        dbUser.setNickname(user.getId());
//        System.out.println(dbUser);
//
//        userService.createUser(dbUser);

        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        } else {
            if (game.isStarted()) {
                if (game.getPlayers().contains(user)) {
                    return ResponseEntity.ok(game);
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 시작된 방입니다.");
            }

            if (game.getRedTeam().getPlayers().contains(user) || game.getBlueTeam().getPlayers().contains(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다른 닉네임을 사용해주세요.");
            }

            if (game.getGameType().equals("BATTLE")) {
                if (game.getRedTeam().getPlayers().size() + game.getBlueTeam().getPlayers().size() == game.getRoomSize()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("정원이 가득찬 방입니다.");
                }

                return ResponseEntity.ok(game);
            } else {
                if (game.getRedTeam().getPlayers().size() == game.getRoomSize()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("정원이 가득찬 방입니다.");
                }

                return ResponseEntity.ok(game);
            }
        }
    }
}
