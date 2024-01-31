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
    private Integer matchedPieceCount;

    @Builder
    public TeamDto(Team team) {
        this.id = team.getId();
        this.gameId = team.getGameId();
        this.matchedPieceCount = team.getMatchedPieceCount();
    }

    public Team toEntity() {
        return Team.builder()
                .id(this.id)
                .gameId(this.gameId)
                .matchedPieceCount(this.matchedPieceCount)
                .build();
    }
}
