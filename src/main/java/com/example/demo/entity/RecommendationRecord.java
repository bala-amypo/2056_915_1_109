
package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_records")
public class RecommendationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private Long purchaseIntentId;
    private Long recommendedCardId;
    private Double expectedRewardValue;
    private String calculationDetailsJson;
    private LocalDateTime recommendedAt;

    @PrePersist
    public void prePersist() {
        this.recommendedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPurchaseIntentId() { return purchaseIntentId; }
    public void setPurchaseIntentId(Long purchaseIntentId) { this.purchaseIntentId = purchaseIntentId; }
    public Long getRecommendedCardId() { return recommendedCardId; }
    public void setRecommendedCardId(Long recommendedCardId) { this.recommendedCardId = recommendedCardId; }
    public Double getExpectedRewardValue() { return expectedRewardValue; }
    public void setExpectedRewardValue(Double expectedRewardValue) { this.expectedRewardValue = expectedRewardValue; }
    public String getCalculationDetailsJson() { return calculationDetailsJson; }
    public void setCalculationDetailsJson(String calculationDetailsJson) { this.calculationDetailsJson = calculationDetailsJson; }
    public LocalDateTime getRecommendedAt() { return recommendedAt; }
    public void setRecommendedAt(LocalDateTime recommendedAt) { this.recommendedAt = recommendedAt; }
}
---------------------------------------------------
package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reward_rules")
public class RewardRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long cardId;
    private String category;
    private String rewardType;
    private Double multiplier;
    private Boolean active;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getRewardType() { return rewardType; }
    public void setRewardType(String rewardType) { this.rewardType = rewardType; }
    public Double getMultiplier() { return multiplier; }
    public void setMultiplier(Double multiplier) { this.multiplier = multiplier; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
---------------------------------------------------------------
package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userId;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) {
            this.role = "USER";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
------------------------------------------------------------------------------
exception:
package com.example.demo.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
------------------------------------------
package com.example.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
}
----------------------------------------------
package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
----------------------------------------------------
repository:
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entity.CreditCardRecord;
import java.util.List;

public interface CreditCardRecordRepository extends JpaRepository<CreditCardRecord, Long> {
    List<CreditCardRecord> findByUserId(Long userId);
    
    @Query("SELECT c FROM CreditCardRecord c WHERE c.userId = ?1 AND c.status = 'ACTIVE'")
    List<CreditCardRecord> findActiveCardsByUser(Long userId);
}
----------------------------------------------------------
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.PurchaseIntentRecord;
import java.util.List;

public interface PurchaseIntentRecordRepository extends JpaRepository<PurchaseIntentRecord, Long> {
    List<PurchaseIntentRecord> findByUserId(Long userId);
}
----------------------------------------------------------
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.RecommendationRecord;
import java.util.List;

public interface RecommendationRecordRepository extends JpaRepository<RecommendationRecord, Long> {
    List<RecommendationRecord> findByUserId(Long userId);
}
-----------------------------------------------------------
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entity.RewardRule;
import java.util.List;

public interface RewardRuleRepository extends JpaRepository<RewardRule, Long> {
    List<RewardRule> findByActiveTrue();
    
    @Query("SELECT r FROM RewardRule r WHERE r.cardId = ?1 AND r.category = ?2 AND r.active = true")
    List<RewardRule> findActiveRulesForCardCategory(Long cardId, String category);
}
-----------------------------------------------------------------
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.UserProfile;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    Optional<UserProfile> findByEmail(String email);
}
-----------------------------------------------------------
service impl:

package com.example.demo.service.impl;

import com.example.demo.service.AuthService;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;

public class AuthServiceImpl implements AuthService {
    
    @Override
    public JwtResponse register(RegisterRequest request) {
        // Implementation would be in AuthController
        return null;
    }
    
    @Override
    public JwtResponse login(LoginRequest request) {
        // Implementation would be in AuthController
        return null;
    }
}
------------------------------------------------------
package com.example.demo.service.impl;

import com.example.demo.service.CreditCardService;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.entity.CreditCardRecord;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRecordRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRecordRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCardRecord addCard(CreditCardRecord card) {
        return creditCardRepository.save(card);
    }

    @Override
    public CreditCardRecord updateCard(Long id, CreditCardRecord updated) {
        CreditCardRecord existing = getCardById(id);
        updated.setId(id);
        return creditCardRepository.save(updated);
    }

    @Override
    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return creditCardRepository.findByUserId(userId);
    }

    @Override
    public CreditCardRecord getCardById(Long id) {
        return creditCardRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    @Override
    public List<CreditCardRecord> getAllCards() {
        return creditCardRepository.findAll();
    }
}
-----------------------------------------------
package com.example.demo.service.impl;

import com.example.demo.service.PurchaseIntentService;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchaseIntentServiceImpl implements PurchaseIntentService {
    private final PurchaseIntentRecordRepository purchaseIntentRepository;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository purchaseIntentRepository) {
        this.purchaseIntentRepository = purchaseIntentRepository;
    }

    @Override
    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) {
        return purchaseIntentRepository.save(intent);
    }

    @Override
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return purchaseIntentRepository.findByUserId(userId);
    }

    @Override
    public PurchaseIntentRecord getIntentById(Long id) {
        return purchaseIntentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
    }

    @Override
    public List<PurchaseIntentRecord> getAllIntents() {
        return purchaseIntentRepository.findAll();
    }
}
-=-----------------------------------------------------------
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
-------------------------------------------------------------------------
package com.example.demo.service.impl;

import com.example.demo.service.RewardRuleService;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.entity.RewardRule;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {
    private final RewardRuleRepository rewardRuleRepository;

    public RewardRuleServiceImpl(RewardRuleRepository rewardRuleRepository) {
        this.rewardRuleRepository = rewardRuleRepository;
    }

    @Override
    public RewardRule createRule(RewardRule rule) {
        if (rule.getMultiplier() != null && rule.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return rewardRuleRepository.save(rule);
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule updated) {
        if (updated.getMultiplier() != null && updated.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        updated.setId(id);
        return rewardRuleRepository.save(updated);
    }

    @Override
    public List<RewardRule> getRulesByCard(Long cardId) {
        return rewardRuleRepository.findAll().stream()
            .filter(r -> cardId.equals(r.getCardId()))
            .toList();
    }

    @Override
    public List<RewardRule> getActiveRules() {
        return rewardRuleRepository.findByActiveTrue();
    }

    @Override
    public List<RewardRule> getAllRules() {
        return rewardRuleRepository.findAll();
    }
}
-------------------------------------------------------------------
package com.example.demo.service.impl;

import com.example.demo.service.UserProfileService;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile createUser(UserProfile profile) {
        if (userProfileRepository.existsByEmail(profile.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (profile.getUserId() != null && userProfileRepository.existsByUserId(profile.getUserId())) {
            throw new BadRequestException("User ID already exists");
        }
        if (profile.getUserId() == null) {
            profile.setUserId(UUID.randomUUID().toString());
        }
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userProfileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByUserId(String userId) {
        return userProfileRepository.findAll().stream()
            .filter(u -> userId.equals(u.getUserId()))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile user = getUserById(id);
        user.setActive(active);
        return userProfileRepository.save(user);
    }
}
-----------------------------------------------------------
service:
package com.example.demo.service;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;

public interface AuthService {
    JwtResponse register(RegisterRequest request);
    JwtResponse login(LoginRequest request);
}
-----------------------------------------
package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;
import java.util.List;

public interface CreditCardService {
    CreditCardRecord addCard(CreditCardRecord card);
    CreditCardRecord updateCard(Long id, CreditCardRecord updated);
    List<CreditCardRecord> getCardsByUser(Long userId);
    CreditCardRecord getCardById(Long id);
    List<CreditCardRecord> getAllCards();
}
------------------------------------------------------------------
package com.example.demo.service;

import com.example.demo.entity.PurchaseIntentRecord;
import java.util.List;

public interface PurchaseIntentService {
    PurchaseIntentRecord createIntent(PurchaseIntentRecord intent);
    List<PurchaseIntentRecord> getIntentsByUser(Long userId);
    PurchaseIntentRecord getIntentById(Long id);
    List<PurchaseIntentRecord> getAllIntents();
}
-------------------------------------------------------------------
package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import java.util.List;

public interface RecommendationEngineService {
    RecommendationRecord generateRecommendation(Long intentId);
    RecommendationRecord getRecommendationById(Long id);
    List<RecommendationRecord> getRecommendationsByUser(Long userId);
    List<RecommendationRecord> getAllRecommendations();
}
---------------------------------------------------------------
package com.example.demo.service;

import com.example.demo.entity.RewardRule;
import java.util.List;

public interface RewardRuleService {
    RewardRule createRule(RewardRule rule);
    RewardRule updateRule(Long id, RewardRule updated);
    List<RewardRule> getRulesByCard(Long cardId);
    List<RewardRule> getActiveRules();
    List<RewardRule> getAllRules();
}
---------------------------------------------------------------------------
package com.example.demo.service;

import com.example.demo.entity.UserProfile;
import java.util.List;

public interface UserProfileService {
    UserProfile createUser(UserProfile profile);
    UserProfile getUserById(Long id);
    UserProfile findByUserId(String userId);
    UserProfile findByEmail(String email);
    List<UserProfile> getAllUsers();
    UserProfile updateUserStatus(Long id, boolean active);
}
--------------------------------------------------------------------------
servelet:

package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/simple-status")
public class SimpleStatusServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.write("Credit Card Reward Maximizer is running");
        writer.flush();
    }
}
----------------------------------------------------------------------