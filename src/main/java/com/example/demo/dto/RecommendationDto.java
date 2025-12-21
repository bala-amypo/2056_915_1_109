package com.example.demo.dto;

import java.time.LocalDateTime;

public class RecommendationDto {
    private Long id;
    private Long userId;
    private Long purchaseIntentId;
    private Long recommendedCardId;
    private Double expectedRewardValue;
    private String calculationDetailsJson;
    private LocalDateTime recommendedAt;

    // Default constructor (for JSON deserialization)
    public RecommendationDto() {}

    // Parameterized constructor (for creating from entity)
    public RecommendationDto(Long id, Long userId, Long purchaseIntentId, Long recommendedCardId, 
                           Double expectedRewardValue, String calculationDetailsJson, 
                           LocalDateTime recommendedAt) {
        this.id = id;
        this.userId = userId;
        this.purchaseIntentId = purchaseIntentId;
        this.recommendedCardId = recommendedCardId;
        this.expectedRewardValue = expectedRewardValue;
        this.calculationDetailsJson = calculationDetailsJson;
        this.recommendedAt = recommendedAt;
    }

    // Constructor from RecommendationRecord entity
    public RecommendationDto(com.example.demo.entity.RecommendationRecord recommendation) {
        this.id = recommendation.getId();
        this.userId = recommendation.getUserId();
        this.purchaseIntentId = recommendation.getPurchaseIntentId();
        this.recommendedCardId = recommendation.getRecommendedCardId();
        this.expectedRewardValue = recommendation.getExpectedRewardValue();
        this.calculationDetailsJson = recommendation.getCalculationDetailsJson();
        this.recommendedAt = recommendation.getRecommendedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPurchaseIntentId() {
        return purchaseIntentId;
    }

    public void setPurchaseIntentId(Long purchaseIntentId) {
        this.purchaseIntentId = purchaseIntentId;
    }

    public Long getRecommendedCardId() {
        return recommendedCardId;
    }

    public void setRecommendedCardId(Long recommendedCardId) {
        this.recommendedCardId = recommendedCardId;
    }

    public Double getExpectedRewardValue() {
        return expectedRewardValue;
    }

    public void setExpectedRewardValue(Double expectedRewardValue) {
        this.expectedRewardValue = expectedRewardValue;
    }

    public String getCalculationDetailsJson() {
        return calculationDetailsJson;
    }

    public void setCalculationDetailsJson(String calculationDetailsJson) {
        this.calculationDetailsJson = calculationDetailsJson;
    }

    public LocalDateTime getRecommendedAt() {
        return recommendedAt;
    }

    public void setRecommendedAt(LocalDateTime recommendedAt) {
        this.recommendedAt = recommendedAt;
    }
}
