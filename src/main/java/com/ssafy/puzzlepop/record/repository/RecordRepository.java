package com.ssafy.puzzlepop.record.repository;

import com.ssafy.puzzlepop.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {
}
