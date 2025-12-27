
package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RewardRuleServiceImpl implements RewardRuleService {
    private final RewardRuleRepository ruleRepo;

    public RewardRuleServiceImpl(RewardRuleRepository ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    public RewardRule createRule(RewardRule rule) {
        if (rule.getMultiplier() == null || rule.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return ruleRepo.save(rule);
    }

    public RewardRule updateRule(Long id, RewardRule updated) {
        RewardRule existing = ruleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
        existing.setMultiplier(updated.getMultiplier());
        existing.setActive(updated.getActive());
        return createRule(existing); // reuse validation
    }

    public List<RewardRule> getRulesByCard(Long cardId) { return ruleRepo.findAll(); /* Simplified for sample */ }
    public List<RewardRule> getActiveRules() { return ruleRepo.findByActiveTrue(); }
    public List<RewardRule> getAllRules() { return ruleRepo.findAll(); }
}
