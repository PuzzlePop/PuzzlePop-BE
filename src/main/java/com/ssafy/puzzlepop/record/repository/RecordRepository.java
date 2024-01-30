package com.ssafy.puzzlepop.record.repository;

import com.ssafy.puzzlepop.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    List<Record> findByUserId(String userId);

    int countByUserId(String userId);
}
