package com.ssafy.puzzlepop.teamuser.domain;

import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.user.domain.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserResponseDto {
    private Long id;
    private TeamDto team;
    private UserDto user;
    private Integer matchedPieceCount;

    public TeamUserResponseDto(TeamUser teamuser) {
        this.id = teamuser.getId();
        this.team = new TeamDto(teamuser.getTeam());
        this.user = new UserDto(teamuser.getUser());
        this.matchedPieceCount = teamuser.getMatchedPieceCount();
    }
}
