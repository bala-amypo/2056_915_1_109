package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;

import java.util.List;

public class RewardRuleServiceImpl implements RewardRuleService {

    private final RewardRuleRepository rewardRuleRepository;

    public RewardRuleServiceImpl(RewardRuleRepository rewardRuleRepository) {
        this.rewardRuleRepository = rewardRuleRepository;
    }

    @Override
    public RewardRule createRule(RewardRule rule) {
        if (rule.getMultiplier() <= 0) {
            throw new RuntimeException("Multiplier must be greater than 0");
        }
        return rewardRuleRepository.save(rule);
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule updated) {
        RewardRule rule = rewardRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));

        rule.setCategory(updated.getCategory());
        rule.setRewardType(updated.getRewardType());
        rule.setMultiplier(updated.getMultiplier());
        rule.setActive(updated.getActive());

        return rewardRuleRepository.save(rule);
    }

    @Override
    public List<RewardRule> getRulesByCard(Long cardId) {
        return rewardRuleRepository.findAll()
                .stream()
                .filter(r -> r.getCardId().equals(cardId))
                .toList();
    }

    @Override
    public List<RewardRule> getActiveRules() {
        return rewardRuleRepository.findByActiveTrue();
    }

    @Override
    public List<RewardRule> getAllRules() {
        return rewardRuleRepository.findAll();
    }
}
