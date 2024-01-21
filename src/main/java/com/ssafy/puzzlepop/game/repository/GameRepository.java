package com.ssafy.puzzlepop.game.repository;

import com.ssafy.puzzlepop.game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByType(String type);
    List<Game> findAllByIsCleared(Boolean isCleared);
    List<Game> findAllByTotalPieceCount(Integer totalPieceCount);
//    List<Game> findAllByPuzzleImageId(Long puzzleImageId);
}
