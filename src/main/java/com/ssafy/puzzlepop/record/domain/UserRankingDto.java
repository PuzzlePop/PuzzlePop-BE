package com.ssafy.puzzlepop.record.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRankingDto {

    private Long userId; // 사용자 id
    private int playedGameCountRank;
    private int soloBattleWinCountRank;
    private int teamBattleWinCountRank;
    private int winningRateRank;
}
