package com.ssafy.puzzlepop.gameinfo.service;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;

import java.util.List;

public interface GameInfoService {
    public GameInfoDto readGameInfo(Long id);
    public List<GameInfoDto> readAllGameInfos();

    public List<GameInfoDto> findAllByType(String type);
    public List<GameInfoDto> findAllByIsCleared(Boolean isCleared);
    public List<GameInfoDto> findAllByCurPlayerCount(Integer curPlayerCount);
    public List<GameInfoDto> findAllByMaxPlayerCount(Integer maxPlayerCount);
    public List<GameInfoDto> findAllByTotalPieceCount(Integer totalPieceCount);

    public Long createGameInfo(GameInfoDto gameInfoDto);
    public Long updateGameInfo(GameInfoDto gameInfoDto);
    public Long deleteGameInfo(Long id);
}
