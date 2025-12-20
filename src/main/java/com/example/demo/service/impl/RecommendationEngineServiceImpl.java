package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;

import java.util.List;

public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PurchaseIntentRecordRepository intentRepo;
    private final UserProfileRepository userRepo;
    private final CreditCardRecordRepository cardRepo;
    private final RewardRuleRepository ruleRepo;
    private final RecommendationRecordRepository recRepo;

    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository intentRepo,
            UserProfileRepository userRepo,
            CreditCardRecordRepository cardRepo,
            RewardRuleRepository ruleRepo,
            RecommendationRecordRepository recRepo
    ) {
        this.intentRepo = intentRepo;
        this.userRepo = userRepo;
        this.cardRepo = cardRepo;
        this.ruleRepo = ruleRepo;
        this.recRepo = recRepo;
    }

    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = intentRepo.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        userRepo.findById(intent.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<CreditCardRecord> cards = cardRepo.findActiveCardsByUser(intent.getUserId());
        if (cards.isEmpty()) throw new BadRequestException("No cards");

        CreditCardRecord best = cards.get(0);
        double bestReward = 0;

        for (CreditCardRecord c : cards) {
            List<RewardRule> rules =
                    ruleRepo.findActiveRulesForCardCategory(c.getId(), intent.getCategory());
            for (RewardRule r : rules) {
                double reward = r.getMultiplier() * intent.getAmount();
                if (reward > bestReward) {
                    bestReward = reward;
                    best = c;
                }
            }
        }

        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(intent.getUserId());
        rec.setPurchaseIntentId(intentId);
        rec.setRecommendedCardId(best.getId());
        rec.setExpectedRewardValue(bestReward);
        rec.setCalculationDetailsJson("{}");

        return recRepo.save(rec);
    }

    public RecommendationRecord getRecommendationById(Long id) {
        return recRepo.findById(id).orElse(null);
    }

    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recRepo.findByUserId(userId);
    }

    public List<RecommendationRecord> getAllRecommendations() {
        return recRepo.findAll();
    }
}