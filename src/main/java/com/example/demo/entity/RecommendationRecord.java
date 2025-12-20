package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RecommendationRecord {

    @Id
    @GeneratedValue
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

    // getters & setters
}