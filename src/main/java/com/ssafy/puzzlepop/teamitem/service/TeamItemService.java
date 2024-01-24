package com.ssafy.puzzlepop.teamitem.service;

public interface TeamItemService {
    public Long createTeamItem(Long teamId, Long itemId);
    public Long updateTeamItem(Long teamId, Long itemId);
    public Long deleteTeamItem(Long id);

}
