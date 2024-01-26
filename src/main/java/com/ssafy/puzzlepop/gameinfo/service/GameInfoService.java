package com.ssafy.puzzlepop.gameinfo.service;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;

import java.util.List;

public interface GameInfoService {
    public List<GameInfoDto> getAllGameInfos();
    public List<GameInfoDto> findAllByType(String type);
    public List<GameInfoDto> findAllByIsCleared(Boolean isCleared);
    public List<GameInfoDto> findAllByTotalPieceCount(Integer totalPieceCount);
    public List<GameInfoDto> findAllByPuzzleImageId(Long puzzleImageId);

    public GameInfoDto getGameInfoById(Long id);
    public Long createGameInfo(GameInfoDto gameInfoDto);
    public Long updateGameInfo(GameInfoDto gameInfoDto);
    public void deleteGameInfo(Long id);
}
