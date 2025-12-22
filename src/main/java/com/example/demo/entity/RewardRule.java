package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reward_rule")
public class RewardRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardId;

    private String ruleType;

    private Integer rewardValue;

    private Boolean active;

    public RewardRule() {
    }

    public RewardRule(Long cardId, String ruleType, Integer rewardValue, Boolean active) {
        this.cardId = cardId;
        this.ruleType = ruleType;
        this.rewardValue = rewardValue;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(Integer rewardValue) {
        this.rewardValue = rewardValue;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
