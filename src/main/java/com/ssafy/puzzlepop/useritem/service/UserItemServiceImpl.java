package com.ssafy.puzzlepop.useritem.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.repository.ItemRepository;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import com.ssafy.puzzlepop.useritem.domain.UserItem;
import com.ssafy.puzzlepop.useritem.domain.UserItemResponseDto;
import com.ssafy.puzzlepop.useritem.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.useritem.exception.UserItemNotFoundException;
import com.ssafy.puzzlepop.useritem.exception.UserNotFoundException;
import com.ssafy.puzzlepop.useritem.repository.UserItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserItemServiceImpl implements UserItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;


    @Override
    public UserItemResponseDto getUserItemById(Long id) {
        UserItem userItem = userItemRepository.findById(id).orElseThrow(
                () -> new UserItemNotFoundException("Item Not Found with id: " + id));
        return new UserItemResponseDto(userItem);
    }

    @Override
    public List<UserItemResponseDto> getUserItemsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found with id: " + userId));
        List<UserItem> userItems = userItemRepository.findAllByUser(user);
        return userItems.stream().map(UserItemResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public Long createUserItem(Long userId, Long itemId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found with id: " + userId));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item Not Found with id: " + itemId));
        UserItem entity = UserItem.builder().user(user).item(item).build();
        return userItemRepository.save(entity).getId();
    }

    @Override
    public Long updateUserItem(Long id, Long itemId) {
        UserItem userItem = userItemRepository.findById(id).orElseThrow(
                () -> new UserItemNotFoundException("Item Not Found with id: " + id));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item Not Found with id: " + itemId));
        userItem.updateItem(item);
        return id;
    }

    @Override
    public Long deleteUserItem(Long id) {
        UserItem userItem = userItemRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("UserItem Not Found with id: " + id));
        userItemRepository.delete(userItem);
        return id;
    }


}

