package com.ssafy.puzzlepop.item.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.domain.ItemDto;

import java.util.List;

public interface ItemService {
    public ItemDto readItem(Long id);
    public List<ItemDto> readAllItem();

    public List<ItemDto> findAllByType(String type);
    public List<ItemDto> findAllByName(String name);
    public List<ItemDto> findAllByPrice(Integer price);
    public List<ItemDto> extractRandomItem(String type, Integer limit);

    public Long createItem(ItemDto itemDto);
    public Long updateItem(ItemDto itemDto);
    public Long deleteItem(Long id);
}
