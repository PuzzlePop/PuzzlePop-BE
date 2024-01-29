package com.ssafy.puzzlepop.record.service;

import com.ssafy.puzzlepop.record.domain.RecordCreateDto;
import com.ssafy.puzzlepop.record.domain.RecordDto;
import com.ssafy.puzzlepop.record.exception.RecordException;

import java.util.List;

public interface RecordService {
    int createRecord(RecordCreateDto recordCreateDto) throws RecordException;

    int updateRecord(RecordDto recordDto) throws RecordException;

    void deleteRecord(int id) throws RecordException;

    RecordDto getRecordById(int id) throws RecordException;

//    List<RecordDto> getAllRecords(String userId) throws RecordException;
//
//    List<RecordDto> getRecordsByType(String type, String userId) throws RecordException;
}
