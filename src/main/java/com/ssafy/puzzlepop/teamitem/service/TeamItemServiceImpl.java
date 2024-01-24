package com.ssafy.puzzlepop.teamitem.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.repository.ItemRepository;
import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import com.ssafy.puzzlepop.teamitem.domain.TeamItem;
import com.ssafy.puzzlepop.teamitem.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.teamitem.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.teamitem.repository.TeamItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamItemServiceImpl implements TeamItemService {
    private final TeamRepository teamRepository;
    private final ItemRepository itemRepository;
    private final TeamItemRepository teamItemRepository;


    @Override
    public Long createTeamItem(Long teamId, Long itemId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new TeamNotFoundException("Team Not Found with id: " + teamId));
        Item item = itemRepository.findById(teamId).orElseThrow(
                () -> new ItemNotFoundException("Item Not Found with id: " + itemId));
        TeamItem entity = TeamItem.builder().team(team).item(item).build();
        return teamItemRepository.save(entity).getId();
    }

    @Override
    public Long updateTeamItem(Long teamId, Long itemId) {
        return null;
    }

    @Override
    public Long deleteTeamItem(Long id) {
        TeamItem entity = teamItemRepository.findById(id).orElseThrow(
                () -> new TeamNotFoundException("TeamItem Not Found with id: " + id));
        teamItemRepository.delete(entity);
        return id;
    }
}
