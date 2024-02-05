package com.ssafy.puzzlepop.teamuser.service;

import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUser;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserResponseDto;
import com.ssafy.puzzlepop.user.domain.UserDto;

import java.util.List;

public interface TeamUserService {
    public Long createTeamUser(TeamUserRequestDto requestDto);
    public Long updateTeamUser(TeamUserRequestDto requestDto);
    public Long deleteTeamUser(Long id);

    public TeamUserResponseDto readTeamUser(Long id);
    public List<TeamUserResponseDto> findAllByTeamId(Long teamId);
    public List<TeamUserResponseDto> findAllByUserId(Long userId);
}
