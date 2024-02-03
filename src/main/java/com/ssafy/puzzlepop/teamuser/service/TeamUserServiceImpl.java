package com.ssafy.puzzlepop.teamuser.service;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import com.ssafy.puzzlepop.teamuser.domain.TeamUser;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserResponseDto;
import com.ssafy.puzzlepop.teamuser.repository.TeamUserRepository;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamUserServiceImpl implements TeamUserService{
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @Override
    @Transactional
    public TeamUserResponseDto readTeamUser(Long id) {
        TeamUser teamUser = teamUserRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("TeamUser Not Found with id: " + id));
        return new TeamUserResponseDto(teamUser);
    }

    @Override
    @Transactional
    public List<TeamUserResponseDto> findAllByTeamId(Long teamId) {
        List<TeamUser> teamUsers = teamUserRepository.findAllByTeamId(teamId);
        return teamUsers.stream().map(TeamUserResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TeamUserResponseDto> findAllByUserId(Long userId) {
        List<TeamUser> teamUsers = teamUserRepository.findAllByUserId(userId);
        return teamUsers.stream().map(TeamUserResponseDto::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public Long createTeamUser(TeamUserRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("User Not Found with id: " + requestDto.getUserId()));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(
                () -> new IllegalArgumentException("Team Not Found with id: " + requestDto.getTeamId()));
        TeamUser teamuser = TeamUser.builder()
                .team(team).user(user)
                .matchedPieceCount(requestDto.getMatchedPieceCount())
                .build();
        return teamUserRepository.save(teamuser).getId();
    }

    @Override
    @Transactional
    public Long updateTeamUser(TeamUserRequestDto requestDto) {
        TeamUser entity = teamUserRepository.findById(requestDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("TeamUser Not Found with id: " + requestDto.getId()));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(
                () -> new IllegalArgumentException("Team Not Found with id: " + requestDto.getTeamId()));
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("User Not Found with id: " + requestDto.getUserId()));
        entity.updateTeam(team); entity.updateUser(user);
        entity.updateMatchedPieceCount(requestDto.getMatchedPieceCount());
        return teamUserRepository.save(entity).getId();
    }

    @Override
    @Transactional
    public Long deleteTeamUser(Long id) {
        TeamUser entity = teamUserRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("TeamUser Not Found with id: " + id));
        teamUserRepository.delete(entity);
        return id;
    }
}
