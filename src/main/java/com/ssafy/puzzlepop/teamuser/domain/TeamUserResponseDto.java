package com.ssafy.puzzlepop.teamuser.domain;

import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.item.domain.ItemDto;
import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.domain.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserResponseDto {
    private Long teamId;
    private List<UserDto> users;

}
