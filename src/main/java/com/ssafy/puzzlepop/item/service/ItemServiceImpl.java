package com.ssafy.puzzlepop.item.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.domain.ItemDto;
import com.ssafy.puzzlepop.item.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemDto readItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id: " + id));
        return new ItemDto(item);
    }

    @Override
    public List<ItemDto> readAllItem() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(ItemDto::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<ItemDto> findAllByType(String type) {
        List<Item> items = itemRepository.findAllByType(type);
        return items.stream().map(ItemDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> findAllByName(String name) {
        List<Item> items = itemRepository.findAllByName(name);
        return items.stream().map(ItemDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> findAllByPrice(Integer price) {
        List<Item> items = itemRepository.findAllByPrice(price);
        return items.stream().map(ItemDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> extractRandomItem(String type, Integer limit) {
        List<Item> items = itemRepository.findAllByType(type);
        Collections.shuffle(items);
        return items.subList(0, limit).stream().map(ItemDto::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Long createItem(ItemDto requestDto) {
        Item item = requestDto.toEntity();
        return itemRepository.save(item).getId();
    }

    @Override
    public Long updateItem(ItemDto requestDto) {
        Item item = itemRepository.findById(requestDto.getId()).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id: " + requestDto.getId()));
        return itemRepository.save(item.update(requestDto)).getId();
    }

    @Override
    public Long deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id: " + id));
        itemRepository.delete(item);
        return id;
    }
}
