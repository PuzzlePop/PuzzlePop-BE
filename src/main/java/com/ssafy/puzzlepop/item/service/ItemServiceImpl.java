package com.ssafy.puzzlepop.item.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.domain.ItemDto;
import com.ssafy.puzzlepop.item.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(ItemDto::new).collect(Collectors.toList());
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id: " + id));
        return new ItemDto(item);
    }

    @Override
    public Long createItem(ItemDto itemDto) {
        Item item = itemRepository.save(itemDto.toEntity());
        return item.getId();
    }

    @Override
    public Long updateItem(ItemDto itemDto) {
        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id: " + itemDto.getId()));
        item.update(itemDto);
        return itemRepository.save(item).getId();
    }

    @Override
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id: " + id));
        itemRepository.delete(item);
    }

    @Override
    public List<ItemDto> findAllByType(String type) {
        List<Item> items = itemRepository.findAllByType(type);
        return items.stream().map(ItemDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> extractRandom(String type, Integer limit) {
        List<Item> items = itemRepository.findAllByType(type);
        Collections.shuffle(items);
        return items.subList(0, limit).stream().map(ItemDto::new).collect(Collectors.toList());
    }
}
