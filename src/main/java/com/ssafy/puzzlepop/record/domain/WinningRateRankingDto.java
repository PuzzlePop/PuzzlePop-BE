package com.ssafy.puzzlepop.record.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.puzzlepop.user.domain.UserDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WinningRateRankingDto implements Comparable<WinningRateRankingDto> {

    private UserDto user;
    private double winningRate;
    private int winCount;
    private int playedGameCount;

    @Override
    public int compareTo(WinningRateRankingDto wrrd) {
        if (this.winningRate > wrrd.winningRate) {
            return 1;
        } else if (this.winningRate < wrrd.winningRate) {
            return -1;
        } else {
            return 0;
        }
    }
}
