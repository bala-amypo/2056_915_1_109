package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardRuleController {

    private final RewardRuleService rewardService;

    public RewardRuleController(RewardRuleService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping
    public RewardRule createRule(@RequestBody RewardRule rule) {
        return rewardService.createRule(rule);
    }

    @PutMapping("/{id}")
    public RewardRule updateRule(@PathVariable Long id,
                                 @RequestBody RewardRule rule) {
        return rewardService.updateRule(id, rule);
    }

    @GetMapping("/card/{cardId}")
    public List<RewardRule> getRulesByCard(@PathVariable Long cardId) {
        return rewardService.getRulesByCard(cardId);
    }

    @GetMapping("/active")
    public List<RewardRule> getActiveRules() {
        return rewardService.getActiveRules();
    }

    @GetMapping
    public List<RewardRule> getAllRules() {
        return rewardService.getAllRules();
    }
}
