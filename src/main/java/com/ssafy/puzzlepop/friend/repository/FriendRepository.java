package com.ssafy.puzzlepop.friend.repository;

import com.ssafy.puzzlepop.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("SELECT f FROM Friend f WHERE f.fromUserId = :userId OR f.toUserId = :userId")
    List<Friend> findAllByFromUserIdOrToUserId(@Param("userId") Long userId);
    List<Friend> findAllByFromUserIdAndRequestStatus(Long fromUserId, String requestStatus);
    List<Friend> findAllByToUserIdAndRequestStatus(Long toUserId, String requestStatus);
    @Query("SELECT f FROM Friend f WHERE (f.fromUserId = :id1 AND f.toUserId = :id2) OR (f.fromUserId = :id2 AND f.toUserId = :id1)")
    Friend findFriendById1AndId2(@Param("id1") Long id1, @Param("id2") Long id2);
}
