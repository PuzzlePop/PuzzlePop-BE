package com.ssafy.puzzlepop.gameinfo.service;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfo;
import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.repository.GameInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameInfoServiceImpl implements GameInfoService {
    private final GameInfoRepository gameInfoRepository;

    @Override
    public List<GameInfoDto> getAllGameInfos() {
        List<GameInfo> gameInfos = gameInfoRepository.findAll();
        return gameInfos.stream().map(GameInfoDto::new).collect(Collectors.toList());
    }

    @Override
    public List<GameInfoDto> findAllByType(String type) {
        return null;
    }

    @Override
    public List<GameInfoDto> findAllByIsCleared(Boolean isCleared) {
        return null;
    }

    @Override
    public List<GameInfoDto> findAllByTotalPieceCount(Integer totalPieceCount) {
        return null;
    }

    @Override
    public List<GameInfoDto> findAllByPuzzleImageId(Long puzzleImageId) {
        return null;
    }

    @Override
    public GameInfoDto getGameInfoById(Long id) {
        GameInfo gameInfo = gameInfoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + id));
        return new GameInfoDto(gameInfo);
    }

    @Override
    public Long createGameInfo(GameInfoDto gameInfoDto) {
        GameInfo gameInfo = gameInfoDto.toEntity();
        return gameInfoRepository.save(gameInfo).getId();
    }

    @Override
    public Long updateGameInfo(GameInfoDto gameInfoDto) {
        GameInfo gameInfo = gameInfoRepository.findById(gameInfoDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + gameInfoDto.getType()));
        return gameInfo.update(gameInfoDto);
    }

    @Override
    public void deleteGameInfo(Long id) {
        GameInfo gameInfo = gameInfoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + id));
        gameInfoRepository.delete(gameInfo);
    }
}
