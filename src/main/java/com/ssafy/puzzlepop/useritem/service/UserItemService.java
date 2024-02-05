package com.ssafy.puzzlepop.useritem.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.useritem.domain.UserItem;
import com.ssafy.puzzlepop.useritem.domain.UserItemResponseDto;
import com.ssafy.puzzlepop.useritem.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.useritem.exception.UserItemNotFoundException;
import com.ssafy.puzzlepop.useritem.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public interface UserItemService {
    public UserItemResponseDto getUserItemById(Long id);
    public List<UserItemResponseDto> getUserItemsByUserId(Long userId);
    public Long createUserItem(Long userId, Long itemId);
    public Long updateUserItem(Long id, Long itemId);
    public Long deleteUserItem(Long id);
}
