package com.example.demo.service;

import com.example.demo.entity.RewardRule;
import java.util.List;

public interface RewardRuleService {

    RewardRule createRule(RewardRule rule);

    RewardRule getRewardRuleById(Long id);

    List<RewardRule> getAllRules();
    
    List<RewardRule> getRulesByCard(Long cardId);   

    List<RewardRule> getActiveRules();       

    RewardRule updateRule(Long id, RewardRule rule);

    void deleteRule(Long id);
}
