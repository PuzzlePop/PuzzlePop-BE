package com.ssafy.puzzlepop.teamitem.service;

import com.ssafy.puzzlepop.teamitem.domain.TeamItemRequestDto;
import com.ssafy.puzzlepop.teamitem.domain.TeamItemResponseDto;

import java.util.List;

public interface TeamItemService {
    public TeamItemResponseDto readTeamItem(Long id);
    public List<TeamItemResponseDto> findAllByTeamId(Long teamId);
    public List<TeamItemResponseDto> findAllByItemId(Long itemId);

    public Long createTeamItem(TeamItemRequestDto requestDto);
    public Long updateTeamItem(TeamItemRequestDto requestDto);
    public Long deleteTeamItem(Long id);

}
