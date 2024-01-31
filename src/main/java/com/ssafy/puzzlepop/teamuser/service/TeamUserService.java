package com.ssafy.puzzlepop.teamuser.service;

import com.ssafy.puzzlepop.teamuser.domain.TeamUserRequestDto;

public interface TeamUserService {
    public Long readTeamUser(Long id);
    public Long createTeamUser(TeamUserRequestDto requestDto);
    public Long updateTeamUser(TeamUserRequestDto requestDto);
    public void deleteTeamUser(Long id);

}
