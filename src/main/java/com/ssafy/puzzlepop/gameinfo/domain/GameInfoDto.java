package com.ssafy.puzzlepop.gameinfo.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GameInfoDto {
    private Long id;
    private String type;
    private Boolean isCleared;

    private Integer curPlayerCount;
    private Integer maxPlayerCount;
    private Integer totalPieceCount;

    private LocalDateTime limitTime;
    private LocalDateTime passedTime;
    private LocalDateTime startedTime;
    private LocalDateTime finishedTime;

    @Builder
    public GameInfoDto(GameInfo gameInfo) {
        this.id = gameInfo.getId();
        this.type = gameInfo.getType();

        this.curPlayerCount = gameInfo.getCurPlayerCount();
        this.maxPlayerCount = gameInfo.getMaxPlayerCount();
        this.totalPieceCount = gameInfo.getTotalPieceCount();

        this.limitTime = gameInfo.getLimitTime();
        this.passedTime = gameInfo.getPassedTime();
        this.startedTime = gameInfo.getStartedTime();
        this.finishedTime = gameInfo.getFinishedTime();
    }

    public GameInfo toEntity() {
        return GameInfo.builder()
                .id(this.id)
                .type(this.type)
                .limitTime(this.limitTime)
                .passedTime(this.passedTime)
                .startedTime(this.startedTime)
                .finishedTime(this.finishedTime)
                .curPlayerCount(this.curPlayerCount)
                .maxPlayerCount(this.maxPlayerCount)
                .totalPieceCount(this.totalPieceCount)
                .build();
    }
}
