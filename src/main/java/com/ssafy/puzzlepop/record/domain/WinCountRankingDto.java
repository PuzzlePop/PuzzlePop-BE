package com.ssafy.puzzlepop.record.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.domain.UserInfoDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WinCountRankingDto implements Comparable<WinCountRankingDto> {

    private UserInfoDto user;
    private int winCount;
    private int playedGameCount;

    @Override
    public int compareTo(WinCountRankingDto wcrd) {
        if (this.winCount > wcrd.winCount) {
            return 1;
        } else if (this.winCount < wcrd.winCount) {
            return -1;
        } else {
            return 0;
        }
    }
}
