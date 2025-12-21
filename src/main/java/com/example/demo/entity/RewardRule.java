package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class RewardRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardId;
    private String category;
    private String rewardType;
    private Double multiplier;
    private Boolean active = true;

    public RewardRule() {}

    public RewardRule(Long cardId, String category, String rewardType, Double multiplier, Boolean active) {
        this.cardId = cardId;
        this.category = category;
        this.rewardType = rewardType;
        this.multiplier = multiplier;
        this.active = active;
    }

    // Getters & Setters
    // ...
}
