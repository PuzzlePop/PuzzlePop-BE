package com.ssafy.puzzlepop.record.service;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfo;
import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.service.GameInfoService;
import com.ssafy.puzzlepop.record.domain.Record;
import com.ssafy.puzzlepop.record.domain.*;
import com.ssafy.puzzlepop.record.exception.RecordException;
import com.ssafy.puzzlepop.record.repository.RecordRepository;
import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.team.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final GameInfoService gameInfoService;
    private final TeamService teamService;
//    private final UserTeamService userTeamService;

    @Autowired
    private RecordServiceImpl(RecordRepository recordRepository, GameInfoService gameInfoService, TeamService teamService/*, UserTeamService userTeamService*/) {
        this.recordRepository = recordRepository;
        this.gameInfoService = gameInfoService;
        this.teamService = teamService;
//        this.userTeamService = userTeamService;
    }

    ///////

    @Override
    public int createRecord(RecordCreateDto recordCreateDto) throws RecordException {
        if (recordCreateDto.getUserId() == null || recordCreateDto.getGameId() <= 0) {
            throw new RecordException("bad request");
        }

        // recordCreateDto의 데이터 기반 엔티티 생성
        Record record = new Record();

        record.setUserId(recordCreateDto.getUserId());
        record.setGameId(recordCreateDto.getGameId());

        // db에 저장
        try {
            record = recordRepository.save(record);
            return record.getId();
        } catch (Exception e) {
            throw new RecordException("error occurred while create dm");
        }

    }

    @Override
    public int updateRecord(RecordDto recordDto) throws RecordException {
        if (recordDto.getId() <= 0 || recordDto.getUserId() == null || recordDto.getGameId() <= 0) {
            throw new RecordException("bad request");
        }

        Record existRecord = recordRepository.findById(recordDto.getId()).orElse(null);

        if (existRecord == null) {
            throw new RecordException("존재하지 않는 기록입니다.");
        }

        existRecord.setUserId(recordDto.getUserId());
        existRecord.setGameId(recordDto.getGameId());

        try {
            recordRepository.save(existRecord);
            return existRecord.getId();
        } catch (Exception e) {
            throw new RecordException("error occurred during update record data");
        }

    }

    @Override
    public void deleteRecord(int id) throws RecordException {
        if (id <= 0) {
            throw new RecordException("bad request");
        }

        Record existRecord = recordRepository.findById(id).orElse(null);
        if (existRecord == null) {
            throw new RecordException("record match to id doesn't exist");
        }

        try {
            recordRepository.deleteById(id);
        } catch (Exception e) {
            throw new RecordException("error occurred during delete record data");
        }
    }

    @Override
    public RecordDto getRecordById(int id) throws RecordException {
        if (id <= 0) {
            throw new RecordException("bad request");
        }

        Record existRecord = recordRepository.findById(id).orElse(null);
        if (existRecord == null) {
            throw new RecordException("record matches to id doesn't exist");
        }

        try {
            return new RecordDto(existRecord);
        } catch (Exception e) {
            throw new RecordException("error occurred during find record data");
        }
    }

    /////////////////

    @Transactional
    @Override
    public List<RecordDetailDto> getRecentRecordsByUserId(String userId) throws RecordException {
        if (userId == null) {
            throw new RecordException("bad request");
        }

        try {
            // userId에 대한 전체 전적 목록
            List<Record> recordList = recordRepository.findByUserId(userId);
            Collections.reverse(recordList); // 최신 순으로 정렬

            // 전적 정보 담을 리스트 생성
            List<RecordDetailDto> recordDetailList = new ArrayList<>();

            // 전적이 20개 이상인 경우, 최근 20개만 거르기
            if (recordList.size() > 20) { // 20개 이상인 경우 리스트 자르기
                recordList.subList(0, 19);
            }

            // 각 recordDetailDto 채우기!
            for (Record record : recordList) {
                RecordDetailDto recordDetailDto = new RecordDetailDto();

                // gameId 바탕으로 gameInfo 가져오기
                GameInfoDto gameInfoDto = gameInfoService.getGameInfoById(record.getGameId());
                recordDetailDto.setGameInfo(gameInfoDto);

                // gameId 바탕으로 teamList 가져오기
                List<TeamDto> teamDtoList = teamService.findAllByGameId(record.getGameId());
                recordDetailDto.setTeamList(teamDtoList);

                // 싱글 게임인 경우
                // 팀 리스트 2개 다 null로 리턴
                if ("single".equals(gameInfoDto.getType())) {
                    recordDetailList.add(recordDetailDto);
                }
                // 협동 게임인 경우
                // teamList1에 담고 teamList2는 null로 리턴
                else if ("multi".equals(gameInfoDto.getType())) {
//  TODO: UserTeam 개발 완료 시 주석 해제
//                    List<UserTeamDto> userTeamDtoList = userTeamService.findAllById(teamDtoList.get(0));
//                    recordDetailDto.setUserTeamList1(userTeamDtoList);
                    recordDetailList.add(recordDetailDto);
                }
                // 배틀 게임인 경우
                // teamList1과 teamList2 모두 정보 담아 리턴
                else if ("battle".equals(gameInfoDto.getType())) {
//  TODO: UserTeam 개발 완료 시 주석 해제
//                    List<UserTeamDto> userTeamDtoList = userTeamService.findAllById(teamDtoList.get(0));
//                    recordDetailDto.setUserTeamList1(userTeamDtoList);
//                    List<UserTeamDto> userTeamDtoList2 = userTeamService.findAllById(teamDtoList.get(1));
//                    recordDetailDto.setUserTeamList2(userTeamDtoList2);
                    recordDetailList.add(recordDetailDto);
                }
                // 셋 다 아님. 여기까지 왔다는 건 뭔가 문제가 있다는 것...
                else {
                    throw new RecordException("something wrong...");
                }
            }

            return recordDetailList;
        } catch (Exception e) {
            throw new RecordException("error occurred during find record data");
        }

    }

    @Transactional
    @Override
    public UserRecordInfoDto getUserRecordInfo(String userId) throws RecordException {

        if (userId == null) {
            throw new RecordException("bad request");
        }

        try {
            UserRecordInfoDto userRecordInfoDto = new UserRecordInfoDto();

            // uid
            userRecordInfoDto.setUserId(userId);

            // 전체 맞춘 피스수
            int totalMatchedPieceCount = 0;
//  TODO: UserTeam 개발 완료 시 주석 해제
//        List<UserTeamDto> userTeamDtoList = userTeamService.getByUserId(userId);
//        for(UserTeamDto utd : userTeamDtoList) {
//            totalMatchedPieceCount += utd.getMatchedPieceCount();
//        }
            userRecordInfoDto.setTotalMatchedPieceCount(totalMatchedPieceCount);

            // 전체 플레이 게임 횟수
            int playedGameCount = recordRepository.countByUserId(userId);
            userRecordInfoDto.setPlayedGameCount(playedGameCount);

            // 배틀게임 플레이 횟수
            int playedBattleGameCount = 0;
            // 이긴 배틀게임 수
            int winCount = 0;
            List<Record> recordList = recordRepository.findByUserId(userId);
            for (Record record : recordList) {
                GameInfoDto gameInfoDto = gameInfoService.getGameInfoById(record.getGameId());
                if ("battle".equals(gameInfoDto.getType())) {
                    playedBattleGameCount++;

                    // TODO: 배틀인 건 알겠고 이 유저가 이겼는지 확인해야 함
                    //
                }
            }
            userRecordInfoDto.setPlayedBattleGameCount(playedBattleGameCount);
            userRecordInfoDto.setWinCount(winCount);

            return userRecordInfoDto;
        } catch (Exception e) {
            throw new RecordException("error occurred during find record info");
        }

    }
}
