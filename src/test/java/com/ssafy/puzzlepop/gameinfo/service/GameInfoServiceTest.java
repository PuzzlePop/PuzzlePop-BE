//package com.ssafy.puzzlepop.gameinfo.service;
//
//import com.ssafy.puzzlepop.gameinfo.domain.GameInfo;
//import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
//import com.ssafy.puzzlepop.gameinfo.exception.GameInfoNotFoundException;
//import com.ssafy.puzzlepop.gameinfo.repository.GameInfoRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class GameInfoServiceTest {
//    @Mock
//    private GameInfoRepository gameInfoRepository;
//    @InjectMocks
//    private GameInfoServiceImpl gameInfoService;
//
//    @BeforeEach
//    void setUp() {
//        Random random = new Random();
//        for (int i = 1; i <= 300; i++) {
//            boolean isCleared = random.nextInt() % 2 == 1;
//            int maxPlayerCount = random.nextInt(1, 5);
//            int curPlayerCount = random.nextInt(1, maxPlayerCount + 1);
//            int totalPieceCount = (int) Math.pow(2, random.nextInt(1, 10));
//
//            LocalDateTime now = LocalDateTime.now();
//            LocalDateTime limitTime = now.plusMinutes(5);
//            LocalDateTime passedTime = now
//                    .plusMinutes(random.nextInt(5))
//                    .plusNanos(random.nextInt(1000));
//            LocalDateTime finishedTime = isCleared ? passedTime : null;
//
//            GameInfo entity = GameInfo.builder()
//                    .type(i % 2 == 1 ? "cooperate" : "battle")
//                    .isCleared(i % 3 == 1)
//                    .curPlayerCount(curPlayerCount * 2)
//                    .maxPlayerCount(maxPlayerCount * 2)
//                    .totalPieceCount(totalPieceCount)
//                    .limitTime(limitTime)
//                    .passedTime(passedTime)
//                    .startedTime(now)
//                    .finishedTime(finishedTime)
//                    .build();
//            gameInfoRepository.save(entity);
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        gameInfoRepository.deleteAll();
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @Test
//    @DisplayName("게임정보 조회()")
//    void readGameInfo() {
//        // when
//        Random random = new Random();
//        GameInfoDto response = gameInfoService.readGameInfo(random.nextLong(300));
//
//        // then
//        assertThat(response).isNotNull();
//        assertThat(response.getId()).isNotNull();
//        assertThat(response.getType()).isNotNull();
//        assertThat(response.getIsCleared()).isNotNull();
//
//        assertThat(response.getCurPlayerCount()).isNotNull();
//        assertThat(response.getMaxPlayerCount()).isNotNull();
//        assertThat(response.getTotalPieceCount()).isNotNull();
//
//        assertThat(response.getLimitTime()).isNotNull();
//        assertThat(response.getLimitTime()).isNotNull();
//        assertThat(response.getPassedTime()).isNotNull();
//        assertThat(response.getStartedTime()).isNotNull();
//        assertThat(response.getFinishedTime()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("게임정보 전체조회()")
//    void readAllGameInfos() {
//        // when
//        List<GameInfoDto> responses = gameInfoService.readAllGameInfos();
//
//        // then
//        assertThat(responses).isNotNull();
//        assertThat(responses.size()).isEqualTo(300);
//    }
//
//    @Test
//    @DisplayName("게임정보 타입별 조회()")
//    void findAllByType() {
//        // when
//        Random random = new Random();
//        List<GameInfoDto> responses1 = gameInfoService.findAllByType("battle");
//        List<GameInfoDto> responses2 = gameInfoService.findAllByType("cooperate");
//
//        // then
//        assertThat(responses1).isNotNull();
//        assertThat(responses2).isNotNull();
//        assertThat(responses1.get(random.nextInt(responses1.size())).getType()).isEqualTo("battle");
//        assertThat(responses2.get(random.nextInt(responses2.size())).getType()).isEqualTo("cooperate");
//    }
//
//    @Test
//    @DisplayName("게임정보 클리어 여부별 조회()")
//    void findAllByIsCleared() {
//        // when
//        Random random = new Random();
//        List<GameInfoDto> responses1 = gameInfoService.findAllByIsCleared(true);
//        List<GameInfoDto> responses2 = gameInfoService.findAllByIsCleared(false);
//
//        // then
//        assertThat(responses1).isNotNull();
//        assertThat(responses2).isNotNull();
//        assertThat(responses1.get(random.nextInt(responses1.size())).getIsCleared()).isTrue();
//        assertThat(responses2.get(random.nextInt(responses2.size())).getIsCleared()).isFalse();
//    }
//
//    @Test
//    @DisplayName("게임정보 참여인원별 조회()")
//    void findAllByCurPlayerCount() {
//        Random random = new Random();
//        for (int i = 2; i <= 8; i = i + 2) {
//            // when
//            List<GameInfoDto> responses = gameInfoService.findAllByCurPlayerCount(i);
//            int index = random.nextInt(responses.size());
//
//            // then
//            assertThat(responses).isNotNull();
//            assertThat(responses.get(index).getCurPlayerCount()).isEqualTo(i);
//        }
//    }
//
//    @Test
//    @DisplayName("게임정보 최대인원별 조회()")
//    void findAllByMaxPlayerCount() {
//        Random random = new Random();
//        for (int i = 2; i <= 8; i = i + 2) {
//            // when
//            List<GameInfoDto> responses = gameInfoService.findAllByMaxPlayerCount(i);
//            int index = random.nextInt(responses.size());
//
//            // then
//            assertThat(responses).isNotNull();
//            assertThat(responses.get(index).getMaxPlayerCount()).isEqualTo(i);
//        }
//    }
//
//    @Test
//    @DisplayName("게임정보 전체피스수별 조회()")
//    void findAllByTotalPieceCount() {
//        Random random = new Random();
//        for (int i = 1; i < 10; i++) {
//            // when
//            int totalPieceCount = (int) Math.pow(2, i);
//            List<GameInfoDto> responses = gameInfoService.findAllByTotalPieceCount(totalPieceCount);
//            int index = random.nextInt(responses.size());
//
//            // then
//            assertThat(responses).isNotNull();
//            assertThat(responses.get(index).getTotalPieceCount()).isEqualTo(totalPieceCount);
//        }
//    }
//
//    @Test
//    @DisplayName("게임정보 삽입()")
//    void createGameInfo() {
//        // given
//        LocalDateTime now = LocalDateTime.now();
//        GameInfoDto gameInfoDto = new GameInfoDto(null, "single", false,
//                4, 8, 256,
//                now.plusMinutes(5), now.plusMinutes(3),
//                now.plusMinutes(0), null);
//
//        GameInfo gameInfo = new GameInfo();
//        gameInfo.setId(1L);
//        gameInfo.setType("single");
//        gameInfo.setIsCleared(false);
//        gameInfo.setCurPlayerCount(4);
//        gameInfo.setMaxPlayerCount(8);
//        gameInfo.setTotalPieceCount(256);
//        gameInfo.setLimitTime(now.plusMinutes(5));
//        gameInfo.setPassedTime(now.plusMinutes(3));
//        gameInfo.setStartedTime(now.plusMinutes(0));
//        gameInfo.setFinishedTime(null);
//
//        when(gameInfoRepository.save(any(GameInfo.class))).thenReturn(gameInfo);
//        when(gameInfoRepository.findById(1L)).thenReturn(Optional.of(gameInfo));
//
//        // when
//        Long id = gameInfoService.createGameInfo(gameInfoDto);
//        GameInfoDto response = gameInfoService.readGameInfo(id);
//
//        // then
//        assertEquals(gameInfoDto.getIsCleared(), response.getIsCleared());
//        assertEquals(gameInfoDto.getType(), response.getType());
//        assertEquals(gameInfoDto.getCurPlayerCount(), response.getCurPlayerCount());
//        assertEquals(gameInfoDto.getMaxPlayerCount(), response.getMaxPlayerCount());
//        assertEquals(gameInfoDto.getTotalPieceCount(), response.getTotalPieceCount());
//        assertEquals(gameInfoDto.getLimitTime(), response.getLimitTime());
//        assertEquals(gameInfoDto.getPassedTime(), response.getPassedTime());
//        assertEquals(gameInfoDto.getStartedTime(), response.getStartedTime());
//        assertEquals(gameInfoDto.getFinishedTime(), response.getFinishedTime());
//    }
//
//    @Test
//    @DisplayName("게임정보 수정()")
//    void updateGameInfo() {
//        // given
//        LocalDateTime now = LocalDateTime.now();
//        GameInfoDto gameInfoDto = gameInfoService.findAllByType("single").get(0);
//        gameInfoDto = new GameInfoDto(gameInfoDto.getId(), "single", true,
//                6, 6, 512,
//                now.plusMinutes(5), now.plusMinutes(3),
//                now.plusMinutes(0), now.plusMinutes(3));
//
//        // when
//        Long id = gameInfoService.updateGameInfo(gameInfoDto);
//        GameInfoDto response = gameInfoService.readGameInfo(id);
//
//        // then
//        assertThat(response).isNotNull();
//        assertThat(response.getId()).isEqualTo(id);
//        assertThat(response.getIsCleared()).isTrue();
//        assertThat(response.getType()).isEqualTo("single");
//
//        assertThat(response.getCurPlayerCount()).isEqualTo(6);
//        assertThat(response.getMaxPlayerCount()).isEqualTo(6);
//        assertThat(response.getTotalPieceCount()).isEqualTo(512);
//
//        assertThat(response.getLimitTime()).isEqualTo(now.plusMinutes(5));
//        assertThat(response.getPassedTime()).isEqualTo(now.plusMinutes(3));
//        assertThat(response.getStartedTime()).isEqualTo(now.plusMinutes(0));
//        assertThat(response.getFinishedTime()).isEqualTo(now.plusMinutes(3));
//    }
//
//    @Test
//    @DisplayName("게임정보 삭제()")
//    void deleteGameInfo() {
//        // given
//        LocalDateTime now = LocalDateTime.now();
//        GameInfoDto gameInfoDto = gameInfoService.findAllByType("single").get(0);
//
//        // when
//        Long id = gameInfoService.deleteGameInfo(gameInfoDto.getId());
//
//        // then
//        assertThrows(GameInfoNotFoundException.class, () -> gameInfoService.readGameInfo(id));
//    }
//}