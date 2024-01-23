package com.ssafy.puzzlepop.team.service;

import com.ssafy.puzzlepop.team.domain.TeamDto;

import java.util.List;

public interface TeamService {
    public List<TeamDto> getAllTeams();
    public TeamDto getTeamById(Long id);
    public Long createTeam(TeamDto teamDto);
    public Long updateTeam(TeamDto teamDto);
    public void deleteTeam(Long id);
    public List<TeamDto> findAllByGameId(Long gameId);
    public Long updateMatchedPieceCount(TeamDto teamDto);
}
