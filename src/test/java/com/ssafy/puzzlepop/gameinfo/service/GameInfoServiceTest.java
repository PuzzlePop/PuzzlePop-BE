package com.ssafy.puzzlepop.gameinfo.service;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfo;
import com.ssafy.puzzlepop.gameinfo.repository.GameInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameInfoServiceTest {
    @Autowired
    private GameInfoRepository gameInfoRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        gameInfoRepository.deleteAll();
    }

    @Test
    void readGameInfo() {
    }

    @Test
    void readAllGameInfos() {
    }

    @Test
    void findAllByType() {
    }

    @Test
    void findAllByIsCleared() {
    }

    @Test
    void findAllByCurPlayerCount() {
    }

    @Test
    void findAllByMaxPlayerCount() {
    }

    @Test
    void findAllByTotalPieceCount() {
    }

    @Test
    @DisplayName("게임정보 삽입()")
    void createGameInfo() {
        // given
        LocalDateTime now = LocalDateTime.now();
        GameInfo entity = GameInfo.builder()
                .type("single")
                .isCleared(false)
                .curPlayerCount(4)
                .maxPlayerCount(8)
                .totalPieceCount(500)
                .limitTime(now.plusMinutes(5))
                .passedTime(now.plusMinutes(3))
                .startedTime(now.plusMinutes(0))
                .finishedTime(null)
                .build();

        // when
        GameInfo response = gameInfoRepository.save(entity);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getIsCleared()).isFalse();
        assertThat(response.getType()).isEqualTo("single");

        assertThat(response.getCurPlayerCount()).isEqualTo(4);
        assertThat(response.getMaxPlayerCount()).isEqualTo(8);
        assertThat(response.getTotalPieceCount()).isEqualTo(500);

        assertThat(response.getLimitTime()).isEqualTo(now.plusMinutes(5));
        assertThat(response.getPassedTime()).isEqualTo(now.plusMinutes(3));
        assertThat(response.getStartedTime()).isEqualTo(now.plusMinutes(0));
        assertThat(response.getFinishedTime()).isNull();
    }

    @Test
    void updateGameInfo() {
        // given

        // when

        // then

    }

    @Test
    void deleteGameInfo() {
        // given

        // when

        // then

    }
}