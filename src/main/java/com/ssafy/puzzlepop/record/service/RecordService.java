package com.ssafy.puzzlepop.record.service;

import com.ssafy.puzzlepop.record.domain.RecordCreateDto;
import com.ssafy.puzzlepop.record.exception.RecordException;

public interface RecordService {
    int createRecord(RecordCreateDto recordCreateDto) throws RecordException;
}
