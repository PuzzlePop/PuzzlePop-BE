package com.ssafy.puzzlepop.team.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gameId;
    private Integer matchedPieceCount;

    public void update(TeamDto teamDto) {
        this.id = teamDto.getId();
        this.gameId = teamDto.getGameId();
        this.matchedPieceCount = teamDto.getMatchedPieceCount();
    }

    public Long updateMatchedPieceCount(Integer count) {
        this.matchedPieceCount = count;
        return this.id;
    }
}
