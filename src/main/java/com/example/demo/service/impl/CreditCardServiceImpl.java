
package com.example.demo.service.impl;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.service.CreditCardService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRecordRepository cardRepo;

    public CreditCardServiceImpl(CreditCardRecordRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public CreditCardRecord addCard(CreditCardRecord card) {
        // FIX: Check if annualFee is not null before comparing it to 0
        if (card.getAnnualFee() != null && card.getAnnualFee() < 0) {
            throw new BadRequestException("Fee must be non-negative");
        }
        return cardRepo.save(card);
    }

    @Override
    public CreditCardRecord updateCard(Long id, CreditCardRecord updated) {
        CreditCardRecord existing = getCardById(id);
        existing.setCardName(updated.getCardName());
        existing.setAnnualFee(updated.getAnnualFee());
        existing.setStatus(updated.getStatus());
        return cardRepo.save(existing);
    }

    @Override
    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return cardRepo.findByUserId(userId);
    }

    @Override
    public CreditCardRecord getCardById(Long id) {
        return cardRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    @Override
    public List<CreditCardRecord> getAllCards() {
        return cardRepo.findAll();
    }
}

package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PurchaseIntentServiceImpl implements PurchaseIntentService {
    private final PurchaseIntentRecordRepository repo;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository repo) { this.repo = repo; }

    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) { return repo.save(intent); }
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) { return repo.findByUserId(userId); }
    public PurchaseIntentRecord getIntentById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
    }
    public List<PurchaseIntentRecord> getAllIntents() { return repo.findAll(); }
}

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

package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RewardRuleServiceImpl implements RewardRuleService {
    private final RewardRuleRepository ruleRepo;

    public RewardRuleServiceImpl(RewardRuleRepository ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    public RewardRule createRule(RewardRule rule) {
        if (rule.getMultiplier() == null || rule.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return ruleRepo.save(rule);
    }

    public RewardRule updateRule(Long id, RewardRule updated) {
        RewardRule existing = ruleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
        existing.setMultiplier(updated.getMultiplier());
        existing.setActive(updated.getActive());
        return createRule(existing); // reuse validation
    }

    public List<RewardRule> getRulesByCard(Long cardId) { return ruleRepo.findAll(); /* Simplified for sample */ }
    public List<RewardRule> getActiveRules() { return ruleRepo.findByActiveTrue(); }
    public List<RewardRule> getAllRules() { return ruleRepo.findAll(); }
}

package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository repo;
    private final PasswordEncoder encoder;

    public UserProfileServiceImpl(UserProfileRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserProfile createUser(UserProfile p) {
        if (repo.existsByEmail(p.getEmail())) throw new BadRequestException("Email already exists");
        if (repo.existsByUserId(p.getUserId())) throw new BadRequestException("User ID already exists");
        
        p.setPassword(encoder.encode(p.getPassword()));
        if (p.getActive() == null) p.setActive(true);
        return repo.save(p);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByUserId(String userId) {
        return repo.findAll().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile user = getUserById(id);
        user.setActive(active);
        return repo.save(user);
    }
}