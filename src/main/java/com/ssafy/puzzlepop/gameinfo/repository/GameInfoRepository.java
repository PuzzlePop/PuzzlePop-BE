package com.ssafy.puzzlepop.gameinfo.repository;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {
    List<GameInfo> findAllByType(String type);
    List<GameInfo> findAllByIsCleared(Boolean isCleared);
    List<GameInfo> findAllByTotalPieceCount(Integer totalPieceCount);
//    List<Game> findAllByPuzzleImageId(Long puzzleImageId);
}
