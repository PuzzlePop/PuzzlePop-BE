package com.ssafy.puzzlepop.friend.service;

import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.domain.FriendRequestRespondDto;
import com.ssafy.puzzlepop.friend.domain.FriendUserInfoDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendService {

    List<FriendDto> getAllByFromUserIdAndRequestStatus(Long fromUserId, String requestStatus);
    List<FriendDto> getAllByToUserIdAndRequestStatus(Long fromUserId, String requestStatus);
    FriendDto getFriendById1AndId2(@Param("id1") Long id1, @Param("id2") Long id2);
    List<FriendDto> getAllByFromUserIdOrToUserId(Long userId);
    List<Long> getAllFriendIdByUserId(Long userId);
    Long createFriend(FriendDto friendDto);
    Long updateFriend(FriendDto friendDto);
    void deleteFriend(FriendDto friendDto);

    List<FriendUserInfoDto> getFriendsByUserIdAndStatus(Long userId, String status);

    FriendDto updateRequestStatus(FriendRequestRespondDto respondDto);
}
