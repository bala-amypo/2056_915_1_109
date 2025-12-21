package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
public class RecommendationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long purchaseIntentId;
    private Long recommendedCardId;
    private Double expectedRewardValue;

    @Column(length = 2000)
    private String calculationDetailsJson;

    private LocalDateTime recommendedAt;

    public RecommendationRecord() {}

    public RecommendationRecord(Long id, Long userId, Long purchaseIntentId,
                                Long recommendedCardId, Double expectedRewardValue,
                                String calculationDetailsJson) {
        this.id = id;
        this.userId = userId;
        this.purchaseIntentId = purchaseIntentId;
        this.recommendedCardId = recommendedCardId;
        this.expectedRewardValue = expectedRewardValue;
        this.calculationDetailsJson = calculationDetailsJson;
    }

    @PrePersist
    public void onCreate() {
        this.recommendedAt = LocalDateTime.now();
    }

    // getters and setters
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
}
