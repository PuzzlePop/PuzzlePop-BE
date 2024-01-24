package com.ssafy.puzzlepop.item.service;

import com.ssafy.puzzlepop.item.domain.ItemDto;

import java.util.List;

public interface ItemService {
    public List<ItemDto> getAllItems();
    public ItemDto getItemById(Long id);
    public Long createItem(ItemDto itemDto);
    public Long updateItem(ItemDto itemDto);
    public void deleteItem(Long id);

    public List<ItemDto> findAllByType(String type);
    public List<ItemDto> extractRandom(String type, Integer limit);
}
