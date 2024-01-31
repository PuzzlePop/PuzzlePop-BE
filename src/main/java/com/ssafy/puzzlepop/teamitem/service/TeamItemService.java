package com.ssafy.puzzlepop.teamitem.service;

import com.ssafy.puzzlepop.teamitem.domain.TeamItemResponseDto;

import java.util.List;

public interface TeamItemService {
    public TeamItemResponseDto getTeamItemById(Long id);
    public List<TeamItemResponseDto> getTeamItemsByTeamId(Long teamId);

    public Long createTeamItem(Long teamId, Long itemId);
    public Long updateTeamItem(Long teamId, Long itemId);
    public Long deleteTeamItem(Long id);

}
