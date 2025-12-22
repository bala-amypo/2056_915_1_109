package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import java.util.List;

public interface RecommendationEngine {

    RecommendationRecord createRecommendation(RecommendationRecord record);

    RecommendationRecord getRecommendationById(Long id);

    List<RecommendationRecord> getRecommendationsByUser(Long userId);

    List<RecommendationRecord> getActiveRecommendations();

    List<RecommendationRecord> getAllRecommendations();
}
