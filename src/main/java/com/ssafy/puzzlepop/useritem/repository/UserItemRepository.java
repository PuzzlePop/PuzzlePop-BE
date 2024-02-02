package com.ssafy.puzzlepop.useritem.repository;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.useritem.domain.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    List<UserItem> findAllByUser(User team);
    List<UserItem> findAllByItem(Item item);
}
