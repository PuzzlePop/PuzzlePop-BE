package com.ssafy.puzzlepop.team.service;

import com.ssafy.puzzlepop.team.domain.TeamDto;

import java.util.List;

public interface TeamService {
    public TeamDto readTeam(Long id);
    public List<TeamDto> readAllTeams();

    public Long createTeam(TeamDto teamDto);
    public Long updateTeam(TeamDto teamDto);
    public Long deleteTeam(Long id);

    public List<TeamDto> findAllByGameId(Long gameId);
    public Long updateMatchedPieceCount(TeamDto teamDto);
}
