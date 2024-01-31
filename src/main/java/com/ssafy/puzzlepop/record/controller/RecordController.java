package com.ssafy.puzzlepop.record.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.puzzlepop.record.domain.RecordCreateDto;
import com.ssafy.puzzlepop.record.domain.RecordDetailDto;
import com.ssafy.puzzlepop.record.domain.RecordDto;
import com.ssafy.puzzlepop.record.domain.UserRecordInfoDto;
import com.ssafy.puzzlepop.record.exception.RecordException;
import com.ssafy.puzzlepop.record.service.RecordService;
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

}
