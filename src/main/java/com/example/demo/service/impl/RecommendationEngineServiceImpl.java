package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;

import java.util.Comparator;
import java.util.List;

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

        if (user.getActive() != null && !user.getActive()) {
            throw new BadRequestException("Inactive user");
        }

        List<CreditCardRecord> cards = creditCardRepository.findActiveCardsByUser(user.getId());
        if (cards.isEmpty()) {
            throw new BadRequestException("No active cards for user");
        }

        double amount = intent.getAmount();
        String category = intent.getCategory();

        CreditCardRecord bestCard = null;
        double bestReward = -1.0;

        for (CreditCardRecord card : cards) {
            List<RewardRule> rules =
                    rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), category);
            double cardReward = rules.stream()
                    .filter(r -> r.getMultiplier() != null && r.getMultiplier() > 0.0)
                    .mapToDouble(r -> r.getMultiplier() * amount)
                    .max()
                    .orElse(0.0);
            if (cardReward > bestReward) {
                bestReward = cardReward;
                bestCard = card;
            }
        }

        if (bestCard == null) {
            throw new BadRequestException("No suitable reward rule");
        }

        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(user.getId());
        rec.setPurchaseIntentId(intent.getId());
        rec.setRecommendedCardId(bestCard.getId());
        rec.setExpectedRewardValue(bestReward);
        rec.setCalculationDetailsJson(
                "{\"amount\":" + amount + ",\"bestReward\":" + bestReward + "}"
        );

        return recommendationRecordRepository.save(rec);
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
