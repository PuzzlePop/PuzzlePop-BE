package com.ssafy.puzzlepop.game.service;

import com.ssafy.puzzlepop.game.domain.GameDto;

import java.util.List;

public interface GameService {
    public List<GameDto> getAllGames();
    public List<GameDto> findAllByType(String type);
    public List<GameDto> findAllByIsCleared(Boolean isCleared);
    public List<GameDto> findAllByTotalPieceCount(Integer totalPieceCount);
    public List<GameDto> findAllByPuzzleImageId(Long puzzleImageId);

    public GameDto getGameById(Long id);
    public Long createGame(GameDto gameDto);
    public Long updateGame(GameDto gameDto);
    public void deleteGame(Long id);
}
