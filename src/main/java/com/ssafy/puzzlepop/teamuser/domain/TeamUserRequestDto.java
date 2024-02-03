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
    private Integer matchedPieceCount;

    @Builder
    public TeamUserRequestDto(TeamUser teamUser) {
        this.id = teamUser.getId();
        this.teamId = teamUser.getTeam().getId();
        this.userId = teamUser.getUser().getId();
        this.matchedPieceCount = teamUser.getMatchedPieceCount();
    }
}
