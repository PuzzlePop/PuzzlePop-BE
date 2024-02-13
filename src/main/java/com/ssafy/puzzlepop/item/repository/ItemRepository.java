package com.ssafy.puzzlepop.item.repository;

import com.ssafy.puzzlepop.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByType(String type);
    List<Item> findAllByName(String name);
    List<Item> findAllByPrice(Integer price);
}
