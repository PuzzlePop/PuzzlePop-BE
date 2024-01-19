package com.ssafy.puzzlepop.item.controller;

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
    public ResponseEntity<?> getItemById(@RequestBody ItemDto requestDto) {
        try {
            ItemDto responseDto = itemService.getItemById(requestDto.getId());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDto);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/item")
    public ResponseEntity<?> createItem(@RequestBody ItemDto requestDto) {
        try {
            Long id = itemService.createItem(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/item")
    public ResponseEntity<?> updateItem(@RequestBody ItemDto requestDto) {
        try {
            Long id = itemService.updateItem(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/item")
    public ResponseEntity<?> deleteItem(@RequestBody ItemDto requestDto) {
        try {
            itemService.deleteItem(requestDto.getId());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/item/list")
    public ResponseEntity<?> findAllItems() {
        try {
            List<ItemDto> responseDtos = itemService.getAllItems();
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/item/search")
    public ResponseEntity<?> findItemsByType(@RequestBody ItemDto requestDto) {
        try {
            List<ItemDto> responseDtos = itemService.findAllByType(requestDto.getType());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDtos);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
