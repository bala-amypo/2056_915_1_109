package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationEngineService service;

    public RecommendationController(RecommendationEngineService service) {
        this.service = service;
    }

    @PostMapping("/generate/{intentId}")
    public RecommendationRecord generate(@PathVariable Long intentId) {
        return service.generateRecommendation(intentId);
    }

    @GetMapping("/user/{userId}")
    public List<RecommendationRecord> getByUser(@PathVariable Long userId) {
        return service.getRecommendationsByUser(userId);
    }

    @GetMapping
    public List<RecommendationRecord> list() {
        return service.getAllRecommendations();
    }
}//RecommendationController