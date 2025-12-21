package com.example.demo.controller;

import com.example.demo.service.RecommendationEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationEngineService recommendationService;

    public RecommendationController(
            RecommendationEngineService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public List<String> getRecommendations(@PathVariable Long userId) {
        return recommendationService.recommendCards(userId);
    }
}
