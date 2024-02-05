package com.ssafy.puzzlepop.useritem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserItemRequestDto {
    private Long id;
    private Long userId;
    private Long itemId;

    @Builder
    public UserItemRequestDto(UserItem userItem) {
        this.id = userItem.getId();
        this.userId = userItem.getUser().getId();
        this.itemId = userItem.getItem().getId();
    }
}