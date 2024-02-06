package com.ssafy.puzzlepop.record.repository;

import com.ssafy.puzzlepop.record.domain.RankingQueryDto;
import com.ssafy.puzzlepop.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUserId(Long userId);
    int countByUserId(Long userId);
    @Query("SELECT NEW com.ssafy.puzzlepop.record.domain.RankingQueryDto(r.userId, COUNT(r.gameId)) FROM Record r GROUP BY r.userId ORDER BY COUNT(r.gameId) DESC")
    List<RankingQueryDto> countGamesByUserId();
}
