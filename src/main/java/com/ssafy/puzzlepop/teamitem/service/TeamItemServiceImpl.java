package com.ssafy.puzzlepop.teamitem.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.repository.ItemRepository;
import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import com.ssafy.puzzlepop.teamitem.domain.TeamItem;
import com.ssafy.puzzlepop.teamitem.domain.TeamItemResponseDto;
import com.ssafy.puzzlepop.teamitem.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.teamitem.exception.TeamItemNotFoundException;
import com.ssafy.puzzlepop.teamitem.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.teamitem.repository.TeamItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamItemServiceImpl implements TeamItemService {
    private final TeamRepository teamRepository;
    private final ItemRepository itemRepository;
    private final TeamItemRepository teamItemRepository;


    @Override
    public TeamItemResponseDto getTeamItemById(Long id) {
        TeamItem teamItem = teamItemRepository.findById(id).orElseThrow(
                () -> new TeamItemNotFoundException("Item Not Found with id: " + id));
        return new TeamItemResponseDto(teamItem);
    }

    @Override
    public List<TeamItemResponseDto> getTeamItemsByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new TeamNotFoundException("Team Not Found with id: " + teamId));
        List<TeamItem> teamItems = teamItemRepository.findAllByTeam(team);
        return teamItems.stream().map(TeamItemResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public Long createTeamItem(Long teamId, Long itemId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new TeamNotFoundException("Team Not Found with id: " + teamId));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item Not Found with id: " + itemId));
        TeamItem entity = TeamItem.builder().team(team).item(item).build();
        return teamItemRepository.save(entity).getId();
    }

    @Override
    public Long updateTeamItem(Long id, Long itemId) {
        TeamItem teamItem = teamItemRepository.findById(id).orElseThrow(
                () -> new TeamItemNotFoundException("Item Not Found with id: " + id));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item Not Found with id: " + itemId));
        teamItem.updateItem(item);
        return id;
    }

    @Override
    public Long deleteTeamItem(Long id) {
        TeamItem teamItem = teamItemRepository.findById(id).orElseThrow(
                () -> new TeamNotFoundException("TeamItem Not Found with id: " + id));
        teamItemRepository.delete(teamItem);
        return id;
    }
}
