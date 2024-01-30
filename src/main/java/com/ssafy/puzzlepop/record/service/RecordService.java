package com.ssafy.puzzlepop.record.service;

import com.ssafy.puzzlepop.record.domain.RecordCreateDto;
import com.ssafy.puzzlepop.record.domain.RecordDetailDto;
import com.ssafy.puzzlepop.record.domain.RecordDto;
import com.ssafy.puzzlepop.record.domain.UserRecordInfoDto;
import com.ssafy.puzzlepop.record.exception.RecordException;

import java.util.List;

public interface RecordService {
    int createRecord(RecordCreateDto recordCreateDto) throws RecordException;

    int updateRecord(RecordDto recordDto) throws RecordException;

    void deleteRecord(int id) throws RecordException;

    RecordDto getRecordById(int id) throws RecordException;

    List<RecordDetailDto> getRecentRecordsByUserId(String userId) throws RecordException;

    UserRecordInfoDto getUserRecordInfo(String userId) throws RecordException;
}
