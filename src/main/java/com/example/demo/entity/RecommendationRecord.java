package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class RecommendationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long purchaseIntentId;
    private Long recommendedCardId;
    private double expectedRewardValue;

    @Column(length = 2000)
    private String calculationDetailsJson;

    public void setPurchaseIntentId(Long id) { this.purchaseIntentId = id; }
    public void setExpectedRewardValue(double v) { this.expectedRewardValue = v; }
    public void setCalculationDetailsJson(String json) { this.calculationDetailsJson = json; }
}