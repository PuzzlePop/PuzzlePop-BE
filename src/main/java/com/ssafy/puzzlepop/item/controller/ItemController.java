package com.ssafy.puzzlepop.item.controller;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.item.service.ItemService;
import com.ssafy.puzzlepop.item.domain.ItemDto;
import com.ssafy.puzzlepop.item.exception.ItemNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/item")
    public ResponseEntity<?> readItem(@RequestParam Long id) {
        ItemDto responseDto = itemService.readItem(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
    }

    @GetMapping("/item/list")
    public ResponseEntity<?> readAllItems() {
        List<ItemDto> responseDtos = itemService.readAllItem();
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/item")
    public ResponseEntity<?> createItem(@RequestBody ItemDto requestDto) {
        Long id = itemService.createItem(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/item")
    public ResponseEntity<?> updateItem(@RequestBody ItemDto requestDto) {
        Long id = itemService.updateItem(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/item")
    public ResponseEntity<?> deleteItem(@RequestParam Long id) {
        id = itemService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/item/search")
    public ResponseEntity<?> findItemsByType(@RequestParam String filter,
                                             @RequestParam String value) {

        List<ItemDto> responseDtos = switch (filter) {
            case "type" -> itemService.findAllByType(value);
            case "name" -> itemService.findAllByName(value);
            case "price" -> itemService.findAllByPrice(Integer.parseInt(value));
            default -> null;
        };
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }

    @GetMapping("/item/random")
    public ResponseEntity<?> extractRandomItemsByType(@RequestParam("type") String type,
                                                      @RequestParam("limit") Integer limit) {
        List<ItemDto> responseDtos = itemService.extractRandomItem(type, limit);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
    }
}
