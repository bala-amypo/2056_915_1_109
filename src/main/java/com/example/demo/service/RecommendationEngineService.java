package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService{

    RecommendationRecord generateRecommendation(Long intentId);

    RecommendationRecord getRecommendationById(Long id);

    List<RecommendationRecord> getRecommendationsByUser(Long userId);

    List<RecommendationRecord> getAllRecommendations();
}
