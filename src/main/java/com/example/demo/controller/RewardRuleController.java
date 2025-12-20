package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
public class RewardRuleController {

    private final RewardRuleService ruleService;

    public RewardRuleController(RewardRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ResponseEntity<RewardRule> create(@RequestBody RewardRule rule) {
        return ResponseEntity.ok(ruleService.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RewardRule> update(@PathVariable Long id,
                                             @RequestBody RewardRule rule) {
        return ResponseEntity.ok(ruleService.updateRule(id, rule));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<RewardRule>> byCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(ruleService.getRulesByCard(cardId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<RewardRule>> active() {
        return ResponseEntity.ok(ruleService.getActiveRules());
    }

    @GetMapping
    public ResponseEntity<List<RewardRule>> all() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }
}
