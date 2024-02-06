package com.ssafy.puzzlepop.record.controller;

import com.ssafy.puzzlepop.record.domain.*;
import com.ssafy.puzzlepop.record.exception.RecordException;
import com.ssafy.puzzlepop.record.service.RecordService;
import org.apache.catalina.WebResourceRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    private RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    /////////

    @PostMapping
    public ResponseEntity<?> addRecord(@RequestBody RecordCreateDto recordCreateDto) throws RecordException {

        Long id = recordService.createRecord(recordCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping
    public ResponseEntity<?> updateRecord(@RequestBody RecordDto recordDto) throws RecordException {

        Long id = recordService.updateRecord(recordDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long id) throws RecordException {

        recordService.deleteRecord(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRecordById(@PathVariable Long id) throws RecordException {

        RecordDto recordDto = recordService.getRecordById(id);
        return ResponseEntity.status(HttpStatus.OK).body(recordDto);
    }

    /////////

    @GetMapping("/list")
    public ResponseEntity<?> findRecentRecordsByUserId(@RequestParam("user_id") Long userId) throws RecordException {

        List<RecordDetailDto> recordList = recordService.getRecentRecordsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(recordList);
    }

    //////////

    @GetMapping("/info")
    public ResponseEntity<?> getRecordInfoByUserId(@RequestParam("user_id") Long userId) throws RecordException {

        UserRecordInfoDto userRecordInfoDto = recordService.getUserRecordInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userRecordInfoDto);
    }

    //////////

    /* Ranking */

    @GetMapping("/ranking/playedGameCount")
    public ResponseEntity<?> getPlayedGameCountRanking() throws RecordException {
        List<PlayedGameCountRankingDto> playedGameCountRanking = recordService.rankPlayedGameCount();
        return ResponseEntity.status(HttpStatus.OK).body(playedGameCountRanking);
    }

    @GetMapping("/ranking/soloBattleWinCountRanking")
    public ResponseEntity<?> getSoloBattleWinCountRanking() throws RecordException{
        List<WinCountRankingDto> soloBattleWinCountRanking = recordService.rankSoloBattleWinCount();
        return ResponseEntity.status(HttpStatus.OK).body(soloBattleWinCountRanking);
    }

    @GetMapping("/ranking/teamBattleWinCountRanking")
    public ResponseEntity<?> getTeamBattleWinCountRanking() throws RecordException{
        List<WinCountRankingDto> teamBattleWinCountRanking = recordService.rankTeamBattleWinCount();
        return ResponseEntity.status(HttpStatus.OK).body(teamBattleWinCountRanking);
    }

    @GetMapping("/ranking/winningRate")
    public ResponseEntity<?> getWinningRateRanking() throws RecordException{
        List<WinningRateRankingDto> winningRateRanking = recordService.rankWinningRate();
        return ResponseEntity.status(HttpStatus.OK).body(winningRateRanking);
    }

    @PostMapping("/ranking/personal")
    public ResponseEntity<?> getRankByUserId(@RequestBody Long userId) throws RecordException{
        UserRankingDto userRankingDto = recordService.getRankByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userRankingDto);
    }

}
