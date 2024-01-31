package com.ssafy.puzzlepop.teamitem.domain;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.team.domain.Team;
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
public class TeamItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public void updateTeam(Team team) {
        this.team = team;
    }

    public void updateItem(Item item) {
        this.item = item;
    }
}
