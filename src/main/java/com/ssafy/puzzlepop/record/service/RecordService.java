package com.ssafy.puzzlepop.record.service;

import com.ssafy.puzzlepop.record.domain.RecordCreateDto;
import com.ssafy.puzzlepop.record.domain.RecordDetailDto;
import com.ssafy.puzzlepop.record.domain.RecordDto;
import com.ssafy.puzzlepop.record.domain.UserRecordInfoDto;
import com.ssafy.puzzlepop.record.exception.RecordException;

import java.util.List;

public interface RecordService {
    Long createRecord(RecordCreateDto recordCreateDto) throws RecordException;

    Long updateRecord(RecordDto recordDto) throws RecordException;

    void deleteRecord(Long id) throws RecordException;

    RecordDto getRecordById(Long id) throws RecordException;

    List<RecordDetailDto> getRecentRecordsByUserId(Long userId) throws RecordException;

    UserRecordInfoDto getUserRecordInfo(Long userId) throws RecordException;
}
