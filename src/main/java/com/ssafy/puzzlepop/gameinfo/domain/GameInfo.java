package com.ssafy.puzzlepop.gameinfo.domain;

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
public class GameInfo {
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

    public Long update(GameInfoDto gameInfoDto) {
        this.type = gameInfoDto.getType();
        this.isCleared = gameInfoDto.getIsCleared();

        this.curPlayerCount = gameInfoDto.getCurPlayerCount();
        this.maxPlayerCount = gameInfoDto.getMaxPlayerCount();
        this.totalPieceCount = gameInfoDto.getTotalPieceCount();

        this.limitTime = gameInfoDto.getLimitTime();
        this.passedTime = gameInfoDto.getPassedTime();
        this.startedTime = gameInfoDto.getStartedTime();
        this.finishedTime = gameInfoDto.getFinishedTime();

//        추후 Image 개발 완료 시 주석해제
//        this.puzzleImageId = gameDto.getPuzzleImageId();
        return this.id;
    }
}
