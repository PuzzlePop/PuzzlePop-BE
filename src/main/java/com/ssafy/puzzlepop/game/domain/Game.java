package com.ssafy.puzzlepop.game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long update(GameDto gameDto) {
        this.type = gameDto.getType();
        this.isCleared = gameDto.getIsCleared();

        this.curPlayerCount = gameDto.getCurPlayerCount();
        this.maxPlayerCount = gameDto.getMaxPlayerCount();
        this.totalPieceCount = gameDto.getTotalPieceCount();

        this.limitTime = gameDto.getLimitTime();
        this.passedTime = gameDto.getPassedTime();
        this.startedTime = gameDto.getStartedTime();
        this.finishedTime = gameDto.getFinishedTime();

//        추후 Image 개발 완료 시 주석해제
//        this.puzzleImageId = gameDto.getPuzzleImageId();
        return this.id;
    }
}
