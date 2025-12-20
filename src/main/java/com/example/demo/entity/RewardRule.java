package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class RewardRule {

    @Id
    @GeneratedValue
    private Long id;

    private Long cardId;
    private String category;
    private String rewardType;
    private Double multiplier;
    private Boolean active;

    // getters & setters
}