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
public class UserRecordInfoDto {

    private String userId; // 사용자 id
    private int totalMatchedPieceCount; // 지금까지 맞춘 전체 피스 수
    private int winCount; // 배틀 게임 이긴 횟수
    private int playedBattleGameCount; // 배틀 게임 플레이 횟수
    private int playedGameCount; // 전체 게임 플레이 횟수
}
