package com.ssafy.puzzlepop.teamuser.service;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.team.repository.TeamRepository;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;
import com.ssafy.puzzlepop.teamuser.repository.TeamUserRepository;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamUserServiceImpl implements TeamUserService{
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @Override
    public Long readTeamUser(Long id) {
        return null;
    }

    @Override
    public Long createTeamUser(TeamUserRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("User Not Found with id: " + requestDto.getUserId()));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(
                () -> new IllegalArgumentException("Team Not Found with id: " + requestDto.getTeamId()));

        return null;
    }

    @Override
    public Long updateTeamUser(TeamUserRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteTeamUser(Long id) {

    }
}
