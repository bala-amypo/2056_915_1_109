package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;

public interface RecommendationEngineService {

    RecommendationRecord generateRecommendation(Long intentId);
}