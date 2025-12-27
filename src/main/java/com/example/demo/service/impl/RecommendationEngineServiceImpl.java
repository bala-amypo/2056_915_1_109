
package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PurchaseIntentRecordRepository intentRepo;
    private final UserProfileRepository userRepo;
    private final CreditCardRecordRepository cardRepo;
    private final RewardRuleRepository ruleRepo;
    private final RecommendationRecordRepository recRepo;

    // Exact constructor order required by the Technical Constraints (Step 0)
    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository intentRepo,
            UserProfileRepository userRepo,
            CreditCardRecordRepository cardRepo,
            RewardRuleRepository ruleRepo,
            RecommendationRecordRepository recRepo) {
        this.intentRepo = intentRepo;
        this.userRepo = userRepo;
        this.cardRepo = cardRepo;
        this.ruleRepo = ruleRepo;
        this.recRepo = recRepo;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        // 1. Fetch the target PurchaseIntentRecord
        PurchaseIntentRecord intent = intentRepo.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        // 2. Load associated UserProfile and check if active
        UserProfile user = userRepo.findById(intent.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (user.getActive() != null && !user.getActive()) {
            throw new BadRequestException("User is not active");
        }

        // 3. Query active cards for the user
        List<CreditCardRecord> activeCards = cardRepo.findActiveCardsByUser(intent.getUserId());
        if (activeCards == null || activeCards.isEmpty()) {
            throw new BadRequestException("No active cards available");
        }

        CreditCardRecord bestCard = null;
        double maxRewardValue = -1.0;

        // 4. Calculate best reward
        for (CreditCardRecord card : activeCards) {
            List<RewardRule> rules = ruleRepo.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            if (rules != null) {
                for (RewardRule rule : rules) {
                    double currentReward = intent.getAmount() * rule.getMultiplier();
                    if (currentReward > maxRewardValue) {
                        maxRewardValue = currentReward;
                        bestCard = card;
                    }
                }
            }
        }

        if (bestCard == null) {
            throw new BadRequestException("No valid reward rule found for category: " + intent.getCategory());
        }

        // 5. Persist and return RecommendationRecord
        RecommendationRecord record = new RecommendationRecord();
        record.setUserId(intent.getUserId());
        record.setPurchaseIntentId(intentId);
        record.setRecommendedCardId(bestCard.getId());
        record.setExpectedRewardValue(maxRewardValue);
        record.setCalculationDetailsJson("{\"calculatedReward\":" + maxRewardValue + "}");
        
        return recRepo.save(record);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recRepo.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recRepo.findAll();
    }
}
