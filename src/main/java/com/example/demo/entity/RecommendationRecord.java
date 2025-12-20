package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RecommendationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long purchaseIntentId;

    private Long recommendedCardId;

    private Double expectedRewardValue;

    @Column(length = 4000)
    private String calculationDetailsJson;

    private LocalDateTime recommendedAt;

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

    public void setRecommendedAt(LocalDateTime recommendedAt) { this.recommendedAt = recommendedAt; }

    @PrePersist
    public void prePersist() {
        if (this.recommendedAt == null) {
            this.recommendedAt = LocalDateTime.now();
        }
        if (this.expectedRewardValue == null) {
            this.expectedRewardValue = 0.0;
        }
    }
}
