package com.example.demo.service.impl;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RecommendationRecordRepository;
import com.example.demo.service.RecommendationEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationEngineImpl implements RecommendationEngine {

    private final RecommendationRecordRepository repository;

    public RecommendationEngineImpl(RecommendationRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public RecommendationRecord createRecommendation(RecommendationRecord record) {
        return repository.save(record);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getActiveRecommendations() {
        return repository.findByActiveTrue();
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return repository.findAll();
    }
}
