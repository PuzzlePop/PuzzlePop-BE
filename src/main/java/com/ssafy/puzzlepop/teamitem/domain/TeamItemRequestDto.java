package com.ssafy.puzzlepop.teamitem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamItemRequestDto {
    private Long id;
    private Long teamId;
    private Long itemId;

    @Builder
    public TeamItemRequestDto(TeamItem teamItem) {
        this.id = teamItem.getId();
        this.teamId = teamItem.getTeam().getId();
        this.itemId = teamItem.getItem().getId();
    }
}
