package com.ssafy.puzzlepop.team.domain;

import com.ssafy.puzzlepop.teamitem.domain.TeamItem;
import com.ssafy.puzzlepop.teamuser.domain.TeamUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    List<TeamItem> teamItems;
    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    List<TeamUser> teamUsers;

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
