package com.ssafy.puzzlepop.item.controller;

import com.ssafy.puzzlepop.item.service.ItemService;
import com.ssafy.puzzlepop.item.domain.ItemDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/item")
    public ResponseEntity<?> readItem(@RequestParam Long id) {
        log.info("ItemController - readItem(): id = " + id);
        ItemDto responseDto = itemService.readItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/item/list")
    public ResponseEntity<?> readAllItems() {
        log.info("ItemController - readAllItems()");
        List<ItemDto> responseDtos = itemService.readAllItem();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/item")
    public ResponseEntity<?> createItem(@RequestBody ItemDto requestDto) {
        log.info("ItemController - createItem(): requestDto = " + requestDto);
        Long id = itemService.createItem(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/item")
    public ResponseEntity<?> updateItem(@RequestBody ItemDto requestDto) {
        log.info("ItemController - updateItem(): requestDto = " + requestDto);
        Long id = itemService.updateItem(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/item")
    public ResponseEntity<?> deleteItem(@RequestParam Long id) {
        log.info("ItemController - deleteItem(): id = " + id);
        id = itemService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/item/search")
    public ResponseEntity<?> findAllByFilter(@RequestParam String filter,
                                             @RequestParam String value) {
        log.info("ItemController - findAllByFilter(): filter = " + filter + ", value = " + value);
        List<ItemDto> responseDtos = switch (filter) {
            case "type" -> itemService.findAllByType(value);
            case "name" -> itemService.findAllByName(value);
            case "price" -> itemService.findAllByPrice(Integer.parseInt(value));
            default -> null;
        };
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("/item/random")
    public ResponseEntity<?> extractRandomItemsByType(@RequestParam("type") String type,
                                                      @RequestParam("limit") Integer limit) {
        log.info("ItemController - extractRandomItemsByType(): type = " + type + ", limit = " + limit);
        List<ItemDto> responseDtos = itemService.extractRandomItem(type, limit);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }
}
