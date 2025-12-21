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
    
    @Column(columnDefinition = "TEXT")
    private String calculationDetailsJson;

    private LocalDateTime recommendedAt;

    public RecommendationRecord() {}

    public RecommendationRecord(Long userId, Long purchaseIntentId, Long recommendedCardId, Double expectedRewardValue, String calculationDetailsJson) {
        this.userId = userId;
        this.purchaseIntentId = purchaseIntentId;
        this.recommendedCardId = recommendedCardId;
        this.expectedRewardValue = expectedRewardValue;
        this.calculationDetailsJson = calculationDetailsJson;
    }

    @PrePersist
    public void prePersist() {
        recommendedAt = LocalDateTime.now();
    }

    // Getters & Setters
    // ...
}
