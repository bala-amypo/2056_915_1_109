package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "reward_rules",
    uniqueConstraints = @UniqueConstraint(columnNames = {"cardId", "category"})
)
@Entity
public class RewardRuleRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long creditCardId;

    private String category;
    private Double rewardRate;

    public RewardRuleRecord() {}

    public Long getId() {
        return id;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    // other getters & setters
}
