package com.ssafy.puzzlepop.teamuser.service;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import com.ssafy.puzzlepop.teamuser.domain.TeamUser;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;
import com.ssafy.puzzlepop.teamuser.repository.TeamUserRepository;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TeamUserServiceImpl implements TeamUserService{
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @Override
    @Transactional
    public TeamUser readTeamUser(Long id) {
        TeamUser teamuser = teamUserRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("TeamUser Not Found with id: " + id));
        return null;
    }


    @Override
    public List<UserDto> findAllByTeamId(Long teamId) {
        List<TeamUser> teamUsers = teamUserRepository.findAllByTeamId(teamId);
        return null;
    }

    @Override
    public List<TeamDto> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public Long createTeamUser(TeamUserRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("User Not Found with id: " + requestDto.getUserId()));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(
                () -> new IllegalArgumentException("Team Not Found with id: " + requestDto.getTeamId()));
        TeamUser entity = TeamUser.builder().team(team).user(user).build();
        return teamUserRepository.save(entity).getId();
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
        entity.updateTeam(team);
        entity.updateUser(user);
        return teamUserRepository.save(entity).getId();
    }

    @Override
    @Transactional
    public void deleteTeamUser(Long id) {
        TeamUser entity = teamUserRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("TeamUser Not Found with id: " + id));
        teamUserRepository.delete(entity);
    }

}
