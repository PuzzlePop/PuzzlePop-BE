package com.ssafy.puzzlepop.gameinfo.service;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfo;
import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.exception.GameInfoNotFoundException;
import com.ssafy.puzzlepop.gameinfo.repository.GameInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameInfoServiceImpl implements GameInfoService {
    private final GameInfoRepository gameInfoRepository;

    @Override
    @Transactional
    public GameInfoDto readGameInfo(Long id) {
        GameInfo gameInfo = gameInfoRepository.findById(id).orElseThrow(
                () -> new GameInfoNotFoundException(id));
        return new GameInfoDto(gameInfo);
    }

    @Override
    @Transactional
    public List<GameInfoDto> readAllGameInfos() {
        List<GameInfo> gameInfos = gameInfoRepository.findAll();
        if (gameInfos.isEmpty()) throw new GameInfoNotFoundException("GameInfo Not Found");
        return gameInfos.stream().map(GameInfoDto::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public List<GameInfoDto> findAllByType(String type) {
        List<GameInfo> gameInfos = gameInfoRepository.findAllByType(type);
        if (gameInfos.isEmpty())
            throw new GameInfoNotFoundException("GameInfo Not Found with type: " + type);
        return gameInfos.stream().map(GameInfoDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GameInfoDto> findAllByIsCleared(Boolean isCleared) {
        List<GameInfo> gameInfos = gameInfoRepository.findAllByIsCleared(isCleared);
        if (gameInfos.isEmpty())
            throw new GameInfoNotFoundException("GameInfo Not Found with isCleared: " + isCleared);
        return gameInfos.stream().map(GameInfoDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GameInfoDto> findAllByCurPlayerCount(Integer curPlayerCount) {
        List<GameInfo> gameInfos = gameInfoRepository.findAllByCurPlayerCount(curPlayerCount);
        if (gameInfos.isEmpty())
            throw new GameInfoNotFoundException("GameInfo Not Found with curPlayerCount: " + curPlayerCount);
        return gameInfos.stream().map(GameInfoDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GameInfoDto> findAllByMaxPlayerCount(Integer maxPlayerCount) {
        List<GameInfo> gameInfos = gameInfoRepository.findAllByMaxPlayerCount(maxPlayerCount);
        if (gameInfos.isEmpty())
            throw new GameInfoNotFoundException("GameInfo Not Found with maxPlayerCount: " + maxPlayerCount);
        return gameInfos.stream().map(GameInfoDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GameInfoDto> findAllByTotalPieceCount(Integer totalPieceCount) {
        List<GameInfo> gameInfos = gameInfoRepository.findAllByTotalPieceCount(totalPieceCount);
        if (gameInfos.isEmpty())
            throw new GameInfoNotFoundException("GameInfo Not Found with totalPieceCount: " + totalPieceCount);
        return gameInfos.stream().map(GameInfoDto::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public Long createGameInfo(GameInfoDto requestDto) {
        GameInfo gameInfo = requestDto.toEntity();
        return gameInfoRepository.save(gameInfo).getId();
    }

    @Override
    @Transactional
    public Long updateGameInfo(GameInfoDto requestDto) {
        GameInfo gameInfo = gameInfoRepository.findById(requestDto.getId()).orElseThrow(
                () -> new GameInfoNotFoundException(requestDto.getId()));
        return gameInfo.update(requestDto);
    }

    @Override
    @Transactional
    public Long deleteGameInfo(Long id) {
        GameInfo gameInfo = gameInfoRepository.findById(id).orElseThrow(
                () -> new GameInfoNotFoundException(id));
        gameInfoRepository.delete(gameInfo);
        return id;
    }
}
