package com.ssafy.puzzlepop.game.service;

import com.ssafy.puzzlepop.game.domain.Game;
import com.ssafy.puzzlepop.game.domain.GameDto;
import com.ssafy.puzzlepop.game.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public List<GameDto> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream().map(GameDto::new).collect(Collectors.toList());
    }

    @Override
    public List<GameDto> findAllByType(String type) {
        return null;
    }

    @Override
    public List<GameDto> findAllByIsCleared(Boolean isCleared) {
        return null;
    }

    @Override
    public List<GameDto> findAllByTotalPieceCount(Integer totalPieceCount) {
        return null;
    }

    @Override
    public List<GameDto> findAllByPuzzleImageId(Long puzzleImageId) {
        return null;
    }

    @Override
    public GameDto getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + id));
        return new GameDto(game);
    }

    @Override
    public Long createGame(GameDto gameDto) {
        Game game = gameDto.toEntity();
        return gameRepository.save(game).getId();
    }

    @Override
    public Long updateGame(GameDto gameDto) {
        Game game = gameRepository.findById(gameDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + gameDto.getType()));
        return game.update(gameDto);
    }

    @Override
    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + id));
        gameRepository.delete(game);
    }
}
