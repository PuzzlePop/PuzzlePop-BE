package com.ssafy.puzzlepop.teamuser.domain;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserDto {
    private Long id;
    private Team team;
    private User user;

    @Builder
    public TeamUserDto(TeamUser teamUser) {
        this.id = teamUser.getId();
        this.team = teamUser.getTeam();
        this.user = teamUser.getUser();
    }

    public TeamUser toEntity() {
        return TeamUser.builder()
                .id(this.id)
                .team(this.team)
                .user(this.user)
                .build();
    }
}
