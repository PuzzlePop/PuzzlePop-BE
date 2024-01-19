package com.ssafy.puzzlepop.team.service;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.team.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(TeamDto::new).collect(Collectors.toList());
    }

    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(
                () -> new TeamNotFoundException("Team not found with id: " + id));
        return new TeamDto(team);
    }


    public Long createTeam(TeamDto teamDto) {
        Team team = teamRepository.save(teamDto.toEntity());
        return team.getId();
    }

    public Long updateTeam(TeamDto teamDto) {
        Team team = teamRepository.findById(teamDto.getId()).orElseThrow(
                () -> new TeamNotFoundException("Team not found with id: " + teamDto.getId()));
        team.updatePieceCount(teamDto.getAssembledPieceCount());
        return teamRepository.save(team).getId();
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(
                () -> new TeamNotFoundException("Team not found with id: " + id));
        teamRepository.delete(team);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<TeamDto> findAllByGameId(Long gameId) {
        List<Team> teams = teamRepository.findAllByGameId(gameId);
        return teams.stream().map(TeamDto::new).collect(Collectors.toList());
    }

}
