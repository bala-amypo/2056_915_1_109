
package com.example.demo.controller;

import com.example.demo.service.RewardRuleService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardRuleController {
    private final RewardRuleService ruleService;
    
    public RewardRuleController(RewardRuleService ruleService) {
        this.ruleService = ruleService;
    }
}
