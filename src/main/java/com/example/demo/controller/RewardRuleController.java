package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
public class RewardRuleController {

    private final RewardRuleService rewardRuleService;

    public RewardRuleController(RewardRuleService rewardRuleService) {
        this.rewardRuleService = rewardRuleService;
    }

    @PostMapping
    public ResponseEntity<RewardRule> addRule(
            @RequestParam Long cardId,
            @Valid @RequestBody RewardRule rule) {

        return ResponseEntity.ok(rewardRuleService.addRule(cardId, rule));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<RewardRule>> getRulesByCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(rewardRuleService.getRulesByCard(cardId));
    }
}
