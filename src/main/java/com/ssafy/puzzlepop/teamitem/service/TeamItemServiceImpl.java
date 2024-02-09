package com.ssafy.puzzlepop.teamitem.service;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.exception.ItemNotFoundException;
import com.ssafy.puzzlepop.item.repository.ItemRepository;
import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import com.ssafy.puzzlepop.teamitem.domain.TeamItem;
import com.ssafy.puzzlepop.teamitem.domain.TeamItemRequestDto;
import com.ssafy.puzzlepop.teamitem.domain.TeamItemResponseDto;
import com.ssafy.puzzlepop.teamitem.exception.TeamItemNotFoundException;
import com.ssafy.puzzlepop.teamitem.repository.TeamItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TeamItemServiceImpl implements TeamItemService {
    private final TeamRepository teamRepository;
    private final ItemRepository itemRepository;
    private final TeamItemRepository teamItemRepository;

    @Override
    public TeamItemResponseDto readTeamItem(Long id) {
        TeamItem teamItem = teamItemRepository.findById(id).orElseThrow(
                () -> new TeamItemNotFoundException(id));
        return new TeamItemResponseDto(teamItem);
    }

    @Override
    public List<TeamItemResponseDto> findAllByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new TeamNotFoundException(teamId));
        List<TeamItem> teamItems = teamItemRepository.findAllByTeam(team);
        return teamItems.stream().map(TeamItemResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<TeamItemResponseDto> findAllByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId));
        List<TeamItem> teamItems = teamItemRepository.findAllByItem(item);
        return teamItems.stream().map(TeamItemResponseDto::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Long createTeamItem(TeamItemRequestDto requestDto) {
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(
                () -> new TeamNotFoundException(requestDto.getTeamId()));
        Item item = itemRepository.findById(requestDto.getItemId()).orElseThrow(
                () -> new ItemNotFoundException(requestDto.getItemId()));
        TeamItem entity = TeamItem.builder().team(team).item(item).build();
        return teamItemRepository.save(entity).getId();
    }

    @Override
    public Long updateTeamItem(TeamItemRequestDto requestDto) {
        TeamItem entity = teamItemRepository.findById(requestDto.getId()).orElseThrow(
                () -> new TeamItemNotFoundException(requestDto.getId()));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(
                () -> new TeamNotFoundException(requestDto.getTeamId()));
        Item item = itemRepository.findById(requestDto.getItemId()).orElseThrow(
                () -> new ItemNotFoundException(requestDto.getItemId()));
        entity.updateTeam(team); entity.updateItem(item);
        return teamItemRepository.save(entity).getId();
    }

    @Override
    public Long deleteTeamItem(Long id) {
        TeamItem entity = teamItemRepository.findById(id).orElseThrow(
                () -> new TeamItemNotFoundException(id));
        teamItemRepository.delete(entity);
        return id;
    }
}
