package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;

import java.util.List;

public class RewardRuleServiceImpl implements RewardRuleService {

    private final RewardRuleRepository repo;

    public RewardRuleServiceImpl(RewardRuleRepository repo) {
        this.repo = repo;
    }

    public RewardRule createRule(RewardRule r) {
        if (r.getMultiplier() <= 0)
            throw new BadRequestException("Price multiplier must be > 0");
        return repo.save(r);
    }

    public RewardRule updateRule(Long id, RewardRule r) {
        r.setId(id);
        return repo.save(r);
    }

    public List<RewardRule> getRulesByCard(Long cardId) {
        return repo.findAll();
    }

    public List<RewardRule> getActiveRules() {
        return repo.findByActiveTrue();
    }

    public List<RewardRule> getAllRules() {
        return repo.findAll();
    }
}