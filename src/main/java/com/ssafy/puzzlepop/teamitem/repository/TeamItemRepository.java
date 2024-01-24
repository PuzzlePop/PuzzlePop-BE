package com.ssafy.puzzlepop.teamitem.repository;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.teamitem.domain.TeamItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamItemRepository extends JpaRepository<TeamItem, Long> {
    List<TeamItem> findAllByTeam(Team team);
    List<TeamItem> findAllByItem(Item item);
}
