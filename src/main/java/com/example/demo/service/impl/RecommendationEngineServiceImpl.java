package com.example.demo.service.impl;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RecommendationRecordRepository;
import com.example.demo.service.RecommendationEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final RecommendationRecordRepository recommendationRecordRepository;

    public RecommendationEngineServiceImpl(
            RecommendationRecordRepository recommendationRecordRepository) {
        this.recommendationRecordRepository = recommendationRecordRepository;
    }

    @Override
    public RecommendationRecord createRecommendation(RecommendationRecord record) {
        return recommendationRecordRepository.save(record);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recommendationRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRecordRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getActiveRecommendations() {
        return recommendationRecordRepository.findByActiveTrue();
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRecordRepository.findAll();
    }
}
