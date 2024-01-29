package com.ssafy.puzzlepop.record.service;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.exception.GameInfoNotFoundException;
import com.ssafy.puzzlepop.gameinfo.service.GameInfoService;
import com.ssafy.puzzlepop.record.domain.Record;
import com.ssafy.puzzlepop.record.domain.RecordCreateDto;
import com.ssafy.puzzlepop.record.domain.RecordDto;
import com.ssafy.puzzlepop.record.exception.RecordException;
import com.ssafy.puzzlepop.record.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final GameInfoService gameInfoService;

    @Autowired
    private RecordServiceImpl(RecordRepository recordRepository, GameInfoService gameInfoService) {
        this.recordRepository = recordRepository;
        this.gameInfoService = gameInfoService;
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
}


//    @Override
//    public List<RecordDto> getAllRecords(String userId) throws RecordException {
//        if (userId == null) {
//            throw new RecordException("bad request");
//        }
//
//        try {
//            List<Record> recordList = recordRepository.findByUserId(userId);
//
//            List<RecordDto> recordDtoList = new ArrayList<>();
//            for (Record record : recordList) {
//                recordDtoList.add(new RecordDto(record));
//            }
//
//            return recordDtoList;
//        } catch (Exception e) {
//            throw new RecordException("error occurred during find record data");
//        }
//
//    }
//
//    @Override
//    public List<RecordDto> getRecordsByType(String type, String userId) throws RecordException {
//        // 1. 해당 user의 전체 전적 목록 불러와서, 플레이한 모든 게임의 id 얻기
//        // 2. 게임 id 가지고 gameinfo 테이블 가서, type 값이 일치하는 게임 id 목록만 불러오기
//        // 3. 해당 게임 id들에 대해 상세 조회해서, RecordResponseDto 뭐 이런 거에 넣어서 리턴하기
//        // => 게임의 전체 정보를 얻기 위해 접근해야 하는 테이블: gameinfo -> team -> user-team(개발전)
//
//        if (type == null || userId == null) {
//            throw new RecordException("bad request");
//        }
//
//        // 해당 유저가 플레이한 모든 게임에 대한 GameInfo List 불러오기
//        List<Record> recordList = recordRepository.findByUserId(userId);
//        List<GameInfoDto> gameInfoList = new ArrayList<>();
//        for (Record record : recordList) {
//            GameInfoDto gameInfoDto = gameInfoService.getGameInfoById(record.getGameId());
//            if (gameInfoDto != null) {
//                gameInfoList.add(gameInfoDto);
//            } else {
//                throw new GameInfoNotFoundException("gameinfo match to id doesn't exist");
//            }
//        }
//
//        // 조회한 type과 일치하는 GameInfo만 남기기
//        for (int i = gameInfoList.size() - 1; i >= 0; --i) {
//            if (!type.equals(gameInfoList.get(i).getType())) {
//
//            }
//        }
//
//    }
//}
