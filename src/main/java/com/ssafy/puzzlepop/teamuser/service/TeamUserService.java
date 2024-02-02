package com.ssafy.puzzlepop.teamuser.service;

import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUser;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;
import com.ssafy.puzzlepop.user.domain.UserDto;

import java.util.List;

public interface TeamUserService {
    public TeamUser readTeamUser(Long id);
    public Long createTeamUser(TeamUserRequestDto requestDto);
    public Long updateTeamUser(TeamUserRequestDto requestDto);
    public void deleteTeamUser(Long id);

    public List<UserDto> findAllByTeamId(Long teamId);
    public List<TeamDto> findAllByUserId(Long userId);
}
