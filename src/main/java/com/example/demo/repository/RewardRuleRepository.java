package com.example.demo.repository;

import com.example.demo.entity.RewardRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRuleRepository extends JpaRepository<RewardRule, Long> {

    List<RewardRule> findByCardId(Long cardId);

    List<RewardRule> findByActiveTrue();
}
