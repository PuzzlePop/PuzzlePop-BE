package com.ssafy.puzzlepop.teamuser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserRequestDto {
    private Long id;
    private Long teamId;
    private Long userId;

    @Builder
    public TeamUserRequestDto(TeamUser teamItem) {
        this.id = teamItem.getId();
        this.teamId = teamItem.getTeam().getId();
        this.userId = teamItem.getUser().getId();
    }
}
