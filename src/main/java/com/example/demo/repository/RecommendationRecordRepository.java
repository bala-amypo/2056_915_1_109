package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.RecommendationRecord;

public interface RecommendationRecordRepository
        extends JpaRepository<RecommendationRecord, Long> {
}
