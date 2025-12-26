
package com.example.demo.controller;

import com.example.demo.service.RecommendationEngineService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {
    private final RecommendationEngineService recommendationService;
    
    public RecommendationController(RecommendationEngineService recommendationService) {
        this.recommendationService = recommendationService;
    }
}

