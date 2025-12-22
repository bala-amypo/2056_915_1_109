package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;
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
        return rewardRuleRepository.save(rule);
    }

    @Override
    public RewardRule getRewardRuleById(Long id) {
        return rewardRuleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reward rule not found with id " + id));
    }

    @Override
    public List<RewardRule> getAllRules() {
        return rewardRuleRepository.findAll();
    }

    @Override
    public List<RewardRule> getRulesByCard(Long cardId) {
        return rewardRuleRepository.findByCardId(cardId);
    }

    @Override
    public List<RewardRule> getActiveRules() {
        return rewardRuleRepository.findByActiveTrue();
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule rule) {

        RewardRule existing = getRewardRuleById(id);

        existing.setCardId(rule.getCardId());
        existing.setRuleType(rule.getRuleType());
        existing.setRewardValue(rule.getRewardValue());
        existing.setActive(rule.getActive());

        return rewardRuleRepository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {

        RewardRule existing = getRewardRuleById(id);
        rewardRuleRepository.delete(existing);
    }
}
