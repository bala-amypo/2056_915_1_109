package com.example.demo.service;

import com.example.demo.entity.RewardRuleRecord;
import java.util.List;

public interface RewardRuleService {

    RewardRuleRecord createRule(RewardRuleRecord rule);

    RewardRuleRecord getRuleById(Long id);

    List<RewardRuleRecord> getRulesByCard(Long cardId);

    List<RewardRuleRecord> getAllRules();
}
