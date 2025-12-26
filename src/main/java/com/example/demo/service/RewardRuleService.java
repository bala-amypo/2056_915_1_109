
package com.example.demo.service.impl;

import com.example.demo.service.RewardRuleService;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.entity.RewardRule;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {
    private final RewardRuleRepository rewardRuleRepository;

    public RewardRuleServiceImpl(RewardRuleRepository rewardRuleRepository) {
        this.rewardRuleRepository = rewardRuleRepository;
    }

    @Override
    public RewardRule createRule(RewardRule rule) {
        if (rule.getMultiplier() != null && rule.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return rewardRuleRepository.save(rule);
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule updated) {
        if (updated.getMultiplier() != null && updated.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        updated.setId(id);
        return rewardRuleRepository.save(updated);
    }

    @Override
    public List<RewardRule> getRulesByCard(Long cardId) {
        return rewardRuleRepository.findAll().stream()
            .filter(r -> cardId.equals(r.getCardId()))
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
