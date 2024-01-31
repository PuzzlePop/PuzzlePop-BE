package com.ssafy.puzzlepop.teamitem.domain;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamItemDto {
    private Long id;
    private Team team;
    private Item item;

    @Builder
    public TeamItemDto(TeamItem teamItem) {
        this.id = teamItem.getId();
        this.team = teamItem.getTeam();
        this.item = teamItem.getItem();
    }

    public TeamItem toEntity() {
        return TeamItem.builder()
                .id(this.id)
                .team(this.team)
                .item(this.item)
                .build();
    }
}
