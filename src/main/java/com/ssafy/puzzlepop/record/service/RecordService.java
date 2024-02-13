package com.ssafy.puzzlepop.record.service;

import com.ssafy.puzzlepop.record.domain.*;
import com.ssafy.puzzlepop.record.exception.RecordException;

import java.util.List;

public interface RecordService {
    Long createRecord(RecordCreateDto recordCreateDto) throws RecordException;

    Long updateRecord(RecordDto recordDto) throws RecordException;

    void deleteRecord(Long id) throws RecordException;

    RecordDto getRecordById(Long id) throws RecordException;

    List<RecordDetailDto> getRecentRecordsByUserId(Long userId) throws RecordException;

    UserRecordInfoDto getUserRecordInfo(Long userId) throws RecordException;

    List<PlayedGameCountRankingDto> rankPlayedGameCount() throws RecordException;

    List<WinCountRankingDto> rankSoloBattleWinCount() throws RecordException;

    List<WinCountRankingDto> rankTeamBattleWinCount() throws RecordException;

    List<WinningRateRankingDto> rankWinningRate() throws RecordException;

    UserRankingDto getRankByUserId(Long userId) throws RecordException;
}
