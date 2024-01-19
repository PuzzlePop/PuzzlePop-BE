package com.ssafy.puzzlepop.team.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private Long id;
    private Long gameId;
    private Integer assembledPieceCount;

    @Builder
    public TeamDto(Team team) {
        this.id = team.getId();
        this.gameId = team.getGameId();
        this.assembledPieceCount = team.getAssembledPieceCount();
    }

    public Team toEntity() {
        return Team.builder()
                .id(this.id)
                .gameId(this.gameId)
                .assembledPieceCount(this.assembledPieceCount)
                .build();
    }
}
