package com.ssafy.puzzlepop.teamuser.service;

import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;

public interface TeamUserService {
    public void createTeamUser(TeamUserRequestDto requestDto);
    public void updateTeamUser(TeamUserRequestDto requestDto);
    public void deleteTeamUser(Long id);

}
