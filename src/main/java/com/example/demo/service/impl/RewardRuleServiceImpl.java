package com.example.demo.service.impl;

import com.example.demo.entity.RewardRuleRecord;
import com.example.demo.repository.RewardRuleRecordRepository;
import com.example.demo.service.RewardRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {

    private final RewardRuleRecordRepository rewardRuleRepository;

    public RewardRuleServiceImpl(
            RewardRuleRecordRepository rewardRuleRepository) {
        this.rewardRuleRepository = rewardRuleRepository;
    }

    @Override
    public RewardRuleRecord createRule(RewardRuleRecord rule) {
        return rewardRuleRepository.save(rule);
    }

    @Override
    public RewardRuleRecord getRuleById(Long id) {
        return rewardRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }

    @Override
    public List<RewardRuleRecord> getRulesByCard(Long cardId) {
        return rewardRuleRepository.findByCreditCardId(cardId);
    }

    @Override
    public List<RewardRuleRecord> getAllRules() {
        return rewardRuleRepository.findAll();
    }
}
