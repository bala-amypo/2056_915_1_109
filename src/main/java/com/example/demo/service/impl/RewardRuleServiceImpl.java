package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.RewardRule;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {

    private final RewardRuleRepository rewardRuleRepository;

    public RewardRuleServiceImpl(RewardRuleRepository rewardRuleRepository) {
        this.rewardRuleRepository = rewardRuleRepository;
    }

    @Override
    public RewardRule addRule(RewardRule rule) {
        return rewardRuleRepository.save(rule);
    }
}