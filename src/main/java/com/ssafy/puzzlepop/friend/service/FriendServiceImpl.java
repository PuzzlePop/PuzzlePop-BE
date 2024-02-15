package com.ssafy.puzzlepop.friend.service;

import com.ssafy.puzzlepop.friend.domain.Friend;
import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.domain.FriendRequestRespondDto;
import com.ssafy.puzzlepop.friend.domain.FriendUserInfoDto;
import com.ssafy.puzzlepop.friend.exception.FriendNotFoundException;
import com.ssafy.puzzlepop.friend.repository.FriendRepository;
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
        if (friend == null) {
            return null;
        }
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
    public Long createFriend(FriendDto friendDto) throws Exception {
        // 나 자신에겐 요청 못 걸게 해야 함 ㅠ
        if(friendDto.getFromUserId().equals(friendDto.getToUserId())) {
            return null;
        }
        // from_user_id && to_user_id로 검색해서 뭔가 있으면 친구 요청 못 생성하게 해야 함
        FriendDto existFriend = getFriendById1AndId2(friendDto.getFromUserId(), friendDto.getToUserId());
        if (existFriend != null) {
            // 이미 친구 관계이거나 요청 보낸 상태임
            return null;
        }
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
    public List<FriendUserInfoDto> getFriendsByUserIdAndStatus(Long userId, String status) {
        List<FriendUserInfoDto> filteredList = new ArrayList<>();

        if ("requested".equals(status)) {
            List<FriendDto> friendList = getAllByToUserIdAndRequestStatus(userId, status);
            for (FriendDto f : friendList) {
                FriendUserInfoDto friendUserInfo = new FriendUserInfoDto();
                friendUserInfo.setFriendId(f.getId());
                friendUserInfo.setFriendUserInfo(userService.getUserById(f.getFromUserId()));

                filteredList.add(friendUserInfo);
            }
        } else {
            List<FriendDto> friendList = getAllByFromUserIdOrToUserId(userId);

            for (FriendDto f : friendList) {
                // 1. status와 일치하는 상태인지 확인
                if (!status.equals(f.getRequestStatus())) {
                    continue;
                }

                FriendUserInfoDto friendUserInfo = new FriendUserInfoDto();
                friendUserInfo.setFriendId(f.getId());

                // 2. 사용자 기준 from인지 to인지 판단해서 dto의 userInfo 세팅
                if (userId.equals(f.getFromUserId())) {
                    friendUserInfo.setFriendUserInfo(userService.getUserById(f.getToUserId()));
                } else if (userId.equals(f.getToUserId())) {
                    friendUserInfo.setFriendUserInfo(userService.getUserById(f.getFromUserId()));
                } else {
                    continue;
                }

                // 3. 리스트에 만든 dto 담기
                filteredList.add(friendUserInfo);
            }
        }

        return filteredList;
    }

    @Override
    public FriendDto updateRequestStatus(FriendRequestRespondDto respondDto) {
        Friend friend = friendRepository.findById(respondDto.getFriendId()).orElseThrow(
                () -> new FriendNotFoundException("Friend not found with id: " + respondDto.getFriendId()));

        friend.setRequestStatus(respondDto.getRespondStatus());
        friendRepository.save(friend);

        return new FriendDto(friend);
    }

}
