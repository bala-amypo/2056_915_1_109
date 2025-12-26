---------------------
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.PurchaseIntentRecord;
import java.util.List;

public interface PurchaseIntentRecordRepository extends JpaRepository<PurchaseIntentRecord, Long> {
    List<PurchaseIntentRecord> findByUserId(Long userId);
}
-------------
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.RecommendationRecord;
import java.util.List;

public interface RecommendationRecordRepository extends JpaRepository<RecommendationRecord, Long> {
    List<RecommendationRecord> findByUserId(Long userId);
}
--------------
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
-------------
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.UserProfile;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    Optional<UserProfile> findByEmail(String email);
}