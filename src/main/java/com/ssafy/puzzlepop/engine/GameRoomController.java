package com.ssafy.puzzlepop.engine;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameRoomController {

    @Autowired
    private final GameService gameService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String room(Model model) {
        return "/chat/room";
    }
//    @ResponseBody
//    public List<Game> rooms(Model model) {
//        return gameService.findAllRoom();
//    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<Game> room() {
        return gameService.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public Game createRoom(@RequestParam String name, @RequestParam String userid) {
        return gameService.createRoom(name, userid);
    }
//    public Game createRoom(@RequestParam String name, @RequestParam String userid, @RequestParam GameType type) {
//        return gameService.createRoom(name, userid, type);
//    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute(roomId);
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Game roomInfo(@PathVariable String roomId) {
        return gameService.findById(roomId);
    }
}
