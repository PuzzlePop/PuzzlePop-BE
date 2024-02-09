package com.ssafy.puzzlepop.friend.service;

import com.ssafy.puzzlepop.friend.domain.Friend;
import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.exception.FriendNotFoundException;
import com.ssafy.puzzlepop.friend.repository.FriendRepository;
import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserService userService;
    private final FriendRepository friendRepository;

    @Override
    public List<FriendDto> getAllByFromUserIdAndRequestStatus(Long fromUserId, String requestStatus) {
        List<Friend> friends = friendRepository.findAllByFromUserIdAndRequestStatus(fromUserId, requestStatus);
        return friends.stream().map(FriendDto::new).collect(Collectors.toList());
    }

    @Override
    public List<FriendDto> getAllByToUserIdAndRequestStatus(Long toUserId, String requestStatus) {
        List<Friend> friends = friendRepository.findAllByToUserIdAndRequestStatus(toUserId, requestStatus);
        return friends.stream().map(FriendDto::new).collect(Collectors.toList());
    }

    @Override
    public FriendDto getFriendById1AndId2(Long id1, Long id2) {
        Friend friend = friendRepository.findFriendById1AndId2(id1, id2);
        return new FriendDto(friend);
    }

    @Override
    public List<FriendDto> getAllByFromUserIdOrToUserId(Long userId) {
        List<Friend> friends = friendRepository.findAllByFromUserIdOrToUserId(userId);
        return friends.stream().map(FriendDto::new).collect(Collectors.toList());
    }

    @Override
    public List<Long> getAllFriendIdByUserId(Long userId) {
        return friendRepository.findAllFriendIdByUserId(userId);
    }

    @Override
    public Long createFriend(FriendDto friendDto) {
        Friend friend = friendRepository.save(friendDto.toEtity());
        return friend.getId();
    }

    @Override
    public Long updateFriend(FriendDto friendDto) {
        Friend friend = friendRepository.findById(friendDto.getId()).orElseThrow(
                () -> new FriendNotFoundException("Friend not found with id: " + friendDto.getId()));
        friend.update(friendDto);
        return friendRepository.save(friend).getId();
    }

    @Override
    public void deleteFriend(FriendDto friendDto) {
        Friend friend = friendRepository.findById(friendDto.getId()).orElseThrow(
                () -> new FriendNotFoundException("Friend not found with id: " + friendDto.getId()));
        friendRepository.delete(friend);
    }

    @Override
    public List<UserDto> getAcceptedFriendsByUserId(Long userId) {
        List<UserDto> filteredList = new ArrayList<>();

        List<Friend> fromList = friendRepository.findAllByFromUserIdAndRequestStatus(userId, "accepted");
        for (Friend f : fromList) {
            filteredList.add(userService.getUserById(f.getToUserId()));
        }
        List<Friend> toList = friendRepository.findAllByToUserIdAndRequestStatus(userId, "accepted");
        for (Friend f : toList) {
            filteredList.add(userService.getUserById(f.getFromUserId()));
        }

        return filteredList;
    }
}
