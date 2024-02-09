package com.ssafy.puzzlepop.team.service;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.team.exception.TeamNotFoundException;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamDto readTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
        return new TeamDto(team);
    }

    public List<TeamDto> readAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(TeamDto::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Long createTeam(TeamDto requestDto) {
        Team team = requestDto.toEntity();
        return teamRepository.save(team).getId();
    }

    public Long updateTeam(TeamDto requestDto) {
        Team team = teamRepository.findById(requestDto.getId()).orElseThrow(
                () -> new TeamNotFoundException(requestDto.getId()));
        return teamRepository.save(team.update(requestDto)).getId();
    }

    public Long deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(
                () -> new TeamNotFoundException(id));
        teamRepository.delete(team);
        return id;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<TeamDto> findAllByGameId(Long gameId) {
        List<Team> teams = teamRepository.findAllByGameId(gameId);
        return teams.stream().map(TeamDto::new).collect(Collectors.toList());
    }

    @Override
    public Long updateMatchedPieceCount(TeamDto requestDto) {
        Team team = teamRepository.findById(requestDto.getId()).orElseThrow(
                () -> new TeamNotFoundException(requestDto.getId()));
        return team.updateMatchedPieceCount(requestDto.getMatchedPieceCount());
    }

}
