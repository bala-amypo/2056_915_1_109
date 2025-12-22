package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {

    private final RewardRuleRepository repository;

    public RewardRuleServiceImpl(RewardRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public RewardRule createRule(RewardRule rule) {
        return repository.save(rule);
    }

    @Override
    public RewardRule getRewardRuleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reward rule not found"));
    }

    @Override
    public List<RewardRule> getAllRules() {
        return repository.findAll();
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule rule) {
        RewardRule existing = getRewardRuleById(id);
        existing.setRuleName(rule.getRuleName());
        existing.setDescription(rule.getDescription());
        return repository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {
        repository.deleteById(id);
    }
}
