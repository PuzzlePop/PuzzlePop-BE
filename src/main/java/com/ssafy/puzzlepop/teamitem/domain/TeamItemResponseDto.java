package com.ssafy.puzzlepop.teamitem.domain;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.domain.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamItemResponseDto {
    private Long id;
    private Long teamId;
    private ItemDto itemInfo;

    @Builder
    public TeamItemResponseDto(TeamItem teamItem) {
        this.id = teamItem.getId();
        this.teamId = teamItem.getTeam().getId();
        this.itemInfo = new ItemDto(teamItem.getItem());
    }

    @Builder
    public TeamItemResponseDto(Long id, Long teamId, Item item) {
        this.id = id;
        this.teamId = teamId;
        this.itemInfo = new ItemDto(item);
    }
}
