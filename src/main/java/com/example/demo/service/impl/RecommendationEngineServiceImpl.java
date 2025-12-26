
package com.example.demo.service.impl;

import com.example.demo.service.RecommendationEngineService;
import com.example.demo.repository.*;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {
    private final PurchaseIntentRecordRepository purchaseIntentRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRecordRepository;

    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository purchaseIntentRepository,
            UserProfileRepository userProfileRepository,
            CreditCardRecordRepository creditCardRepository,
            RewardRuleRepository rewardRuleRepository,
            RecommendationRecordRepository recommendationRecordRepository) {
        this.purchaseIntentRepository = purchaseIntentRepository;
        this.userProfileRepository = userProfileRepository;
        this.creditCardRepository = creditCardRepository;
        this.rewardRuleRepository = rewardRuleRepository;
        this.recommendationRecordRepository = recommendationRecordRepository;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = purchaseIntentRepository.findById(intentId)
            .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
        
        UserProfile user = userProfileRepository.findById(intent.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        List<CreditCardRecord> activeCards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
        if (activeCards.isEmpty()) {
            throw new BadRequestException("No active cards found for user");
        }
        
        CreditCardRecord bestCard = null;
        double maxReward = 0.0;
        
        for (CreditCardRecord card : activeCards) {
            List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            for (RewardRule rule : rules) {
                double reward = intent.getAmount() * rule.getMultiplier();
                if (reward > maxReward) {
                    maxReward = reward;
                    bestCard = card;
                }
            }
        }
        
        if (bestCard == null) {
            bestCard = activeCards.get(0);
            maxReward = 0.0;
        }
        
        RecommendationRecord recommendation = new RecommendationRecord();
        recommendation.setUserId(intent.getUserId());
        recommendation.setPurchaseIntentId(intentId);
        recommendation.setRecommendedCardId(bestCard.getId());
        recommendation.setExpectedRewardValue(maxReward);
        recommendation.setCalculationDetailsJson("{\"cardId\":" + bestCard.getId() + ",\"reward\":" + maxReward + "}");
        
        return recommendationRecordRepository.save(recommendation);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recommendationRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRecordRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRecordRepository.findAll();
    }
}

