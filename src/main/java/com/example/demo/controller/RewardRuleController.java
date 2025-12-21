package com.example.demo.controller;

import com.example.demo.entity.RewardRuleRecord;
import com.example.demo.service.RewardRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reward-rules")
public class RewardRuleController {

    private final RewardRuleService rewardRuleService;

    public RewardRuleController(RewardRuleService rewardRuleService) {
        this.rewardRuleService = rewardRuleService;
    }

    @PostMapping
    public RewardRuleRecord create(@RequestBody RewardRuleRecord rule) {
        return rewardRuleService.createRule(rule);
    }

    @GetMapping("/{id}")
    public RewardRuleRecord getById(@PathVariable Long id) {
        return rewardRuleService.getRuleById(id);
    }

    @GetMapping("/card/{cardId}")
    public List<RewardRuleRecord> getByCard(@PathVariable Long cardId) {
        return rewardRuleService.getRulesByCard(cardId);
    }

    @GetMapping
    public List<RewardRuleRecord> getAll() {
        return rewardRuleService.getAllRules();
    }
}
