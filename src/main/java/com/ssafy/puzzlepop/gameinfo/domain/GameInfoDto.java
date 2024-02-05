package com.ssafy.puzzlepop.gameinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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

//    추후 Image 개발 완료 시 주석해제
//    private Long puzzleImageId;
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

//        추후 Image 개발 완료 시 주석해제
//        this.puzzleImageId = game.getPuzzleImageId();
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
//                .puzzleImageId(this.puzzleImageId)
                .build();
    }
}
