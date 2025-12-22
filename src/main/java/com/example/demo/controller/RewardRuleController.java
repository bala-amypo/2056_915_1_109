package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
public class RewardRuleController {

    @Autowired
    private RewardRuleService rewardRuleService;

    @PostMapping
    public RewardRule createRule(@RequestBody RewardRule rule) {
        return rewardRuleService.createRule(rule);
    }

    @PutMapping("/{id}")
    public RewardRule updateRule(@PathVariable Long id,
                                 @RequestBody RewardRule rule) {
        return rewardRuleService.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public RewardRule getRuleById(@PathVariable Long id) {
        return rewardRuleService.getRewardRuleById(id);
    }

    @GetMapping("/card/{cardId}")
    public List<RewardRule> getRulesByCard(@PathVariable Long cardId) {
        return rewardRuleService.getRulesByCard(cardId);
    }

    @GetMapping("/active")
    public List<RewardRule> getActiveRules() {
        return rewardRuleService.getActiveRules();
    }

    @GetMapping
    public List<RewardRule> getAllRules() {
        return rewardRuleService.getAllRules();
    }
}
