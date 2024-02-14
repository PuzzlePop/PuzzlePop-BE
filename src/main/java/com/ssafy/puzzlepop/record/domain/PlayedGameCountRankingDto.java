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
public class PlayedGameCountRankingDto implements Comparable<PlayedGameCountRankingDto> {

    private UserInfoDto user; // 사용자 정보
    private int playedGameCount; // 전체 플레이한 게임 수

    @Override
    public int compareTo(PlayedGameCountRankingDto o) {
        if (this.playedGameCount > o.playedGameCount) {
            return 1;
        } else if (this.playedGameCount < o.playedGameCount) {
            return -1;
        } else {
            return 0;
        }
    }
}
