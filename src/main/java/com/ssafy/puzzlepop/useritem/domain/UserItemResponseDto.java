package com.ssafy.puzzlepop.useritem.domain;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserItemResponseDto {
    private Long id;
    private User user;
    private Item item;

    @Builder
    public UserItemResponseDto(UserItem userItem) {
        this.id = userItem.getId();
        this.user = userItem.getUser();
        this.item = userItem.getItem();
    }

    public UserItem toEntity() {
        return UserItem.builder()
                .id(this.id)
                .user(this.user)
                .item(this.item)
                .build();
    }
}
