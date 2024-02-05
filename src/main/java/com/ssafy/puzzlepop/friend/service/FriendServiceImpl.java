package com.ssafy.puzzlepop.friend.service;

import com.ssafy.puzzlepop.friend.domain.Friend;
import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.exception.FriendNotFoundException;
import com.ssafy.puzzlepop.friend.repository.FriendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService{

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
}
