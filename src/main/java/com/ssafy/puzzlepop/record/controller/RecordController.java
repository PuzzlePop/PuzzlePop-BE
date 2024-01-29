package com.ssafy.puzzlepop.record.controller;

import com.ssafy.puzzlepop.record.domain.Record;
import com.ssafy.puzzlepop.record.domain.RecordCreateDto;
import com.ssafy.puzzlepop.record.domain.RecordDto;
import com.ssafy.puzzlepop.record.exception.RecordException;
import com.ssafy.puzzlepop.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: userId 입력받는 메소드들: RequestParam 대신 Authentication에서 받아오도록 추후 리팩토링 필요

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

        int id = recordService.createRecord(recordCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping
    public ResponseEntity<?> updateRecord(@RequestBody RecordDto recordDto) throws RecordException {

        int id = recordService.updateRecord(recordDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable int id) throws RecordException {

        recordService.deleteRecord(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRecordById(@PathVariable int id) throws RecordException {

        RecordDto recordDto = recordService.getRecordById(id);
        return ResponseEntity.status(HttpStatus.OK).body(recordDto);
    }

//    @GetMapping("/list")
//    public ResponseEntity<?> findAllRecords(@RequestParam String userId) throws RecordException {
//
//        List<RecordDto> recordList = recordService.getAllRecords(userId);
//        return ResponseEntity.status(HttpStatus.OK).body(recordList);
//    }
//
//    @GetMapping("/list/{type}")
//    public ResponseEntity<?> findRecordsByType(@PathVariable String type, @RequestParam String userId) throws RecordException {
//
//        List<RecordDto> recordList = recordService.getRecordsByType(type, userId);
//        return ResponseEntity.status(HttpStatus.OK).body(recordList);
//    }

    /////////////


}
