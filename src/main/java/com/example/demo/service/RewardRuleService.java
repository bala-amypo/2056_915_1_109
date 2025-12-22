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

    @GetMapping("/{id}")
    public RewardRule getRewardRuleById(@PathVariable Long id) {
        return rewardRuleService.getRewardRuleById(id);
    }

    @GetMapping
    public List<RewardRule> getAllRules() {
        return rewardRuleService.getAllRules();
    }
}
