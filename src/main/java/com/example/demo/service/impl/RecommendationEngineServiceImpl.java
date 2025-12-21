package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;

import java.util.List;

public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PurchaseIntentRecordRepository intentRepository;
    private final UserProfileRepository userRepository;
    private final CreditCardRecordRepository cardRepository;
    private final RewardRuleRepository ruleRepository;
    private final RecommendationRecordRepository recommendationRepository;

    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository intentRepository,
            UserProfileRepository userRepository,
            CreditCardRecordRepository cardRepository,
            RewardRuleRepository ruleRepository,
            RecommendationRecordRepository recommendationRepository) {

        this.intentRepository = intentRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.ruleRepository = ruleRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {

        PurchaseIntentRecord intent = intentRepository.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        userRepository.findById(intent.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<CreditCardRecord> cards =
                cardRepository.findActiveCardsByUser(intent.getUserId());

        double maxReward = 0;
        Long bestCardId = null;

        for (CreditCardRecord card : cards) {
            List<RewardRule> rules =
                    ruleRepository.findActiveRulesForCardCategory(
                            card.getId(), intent.getCategory());

            for (RewardRule rule : rules) {
                double reward = intent.getAmount() * rule.getMultiplier();
                if (reward > maxReward) {
                    maxReward = reward;
                    bestCardId = card.getId();
                }
            }
        }

        RecommendationRecord record = new RecommendationRecord(
                null,
                intent.getUserId(),
                intentId,
                bestCardId,
                maxReward,
                "{\"status\":\"calculated\"}"
        );

        return recommendationRepository.save(record);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRepository.findAll();
    }
}
