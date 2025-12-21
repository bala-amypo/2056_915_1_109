package com.example.demo.repository;

import com.example.demo.entity.RewardRuleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRuleRecordRepository
        extends JpaRepository<RewardRuleRecord, Long> {

    List<RewardRuleRecord> findByCreditCardId(Long creditCardId);
}
