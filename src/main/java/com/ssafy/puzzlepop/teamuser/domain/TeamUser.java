package com.ssafy.puzzlepop.teamuser.domain;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer matchedPieceCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void updateTeam(Team team) {
        this.team = team;
    }
    public void updateUser(User user) {
        this.user = user;
    }
    public void updateMatchedPieceCount(Integer matchedPieceCount) {
        this.matchedPieceCount = matchedPieceCount;
    }
}
