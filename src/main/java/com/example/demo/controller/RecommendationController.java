package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationEngine recommendationEngine;

    @PostMapping
    public RecommendationRecord create(
            @RequestBody RecommendationRecord record) {
        return recommendationEngine.createRecommendation(record);
    }

    @GetMapping("/{id}")
    public RecommendationRecord getById(@PathVariable Long id) {
        return recommendationEngine.getRecommendationById(id);
    }

    @GetMapping("/user/{userId}")
    public List<RecommendationRecord> getByUser(@PathVariable Long userId) {
        return recommendationEngine.getRecommendationsByUser(userId);
    }

    @GetMapping("/active")
    public List<RecommendationRecord> getActive() {
        return recommendationEngine.getActiveRecommendations();
    }

    @GetMapping
    public List<RecommendationRecord> getAll() {
        return recommendationEngine.getAllRecommendations();
    }
}
