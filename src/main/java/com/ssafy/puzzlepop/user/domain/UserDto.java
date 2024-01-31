package com.ssafy.puzzlepop.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @Builder
    public UserDto(User user) {
        this.id = user.getId();
    }
}
