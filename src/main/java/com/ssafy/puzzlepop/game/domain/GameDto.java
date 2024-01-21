package com.ssafy.puzzlepop.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
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
    public GameDto(Game game) {
        this.id = game.getId();
        this.type = game.getType();

        this.curPlayerCount = game.getCurPlayerCount();
        this.maxPlayerCount = game.getMaxPlayerCount();
        this.totalPieceCount = game.getTotalPieceCount();

        this.limitTime = game.getLimitTime();
        this.passedTime = game.getPassedTime();
        this.startedTime = game.getStartedTime();
        this.finishedTime = game.getFinishedTime();

//        추후 Image 개발 완료 시 주석해제
//        this.puzzleImageId = game.getPuzzleImageId();
    }

    public Game toEntity() {
        return Game.builder()
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
