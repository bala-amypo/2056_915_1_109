package com.example.demo.dto;

public class RewardRuleDto {
    private Long id;
    private Long cardId;
    private String category;
    private String rewardType;
    private Double multiplier;
    private Boolean active;

    // Default constructor (for JSON deserialization)
    public RewardRuleDto() {}

    // Parameterized constructor (for creating from entity)
    public RewardRuleDto(Long id, Long cardId, String category, String rewardType, 
                        Double multiplier, Boolean active) {
        this.id = id;
        this.cardId = cardId;
        this.category = category;
        this.rewardType = rewardType;
        this.multiplier = multiplier;
        this.active = active;
    }

    // Constructor from RewardRule entity
    public RewardRuleDto(com.example.demo.entity.RewardRule rule) {
        this.id = rule.getId();
        this.cardId = rule.getCardId();
        this.category = rule.getCategory();
        this.rewardType = rule.getRewardType();
        this.multiplier = rule.getMultiplier();
        this.active = rule.getActive();
    }

    // Getters and Setters
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
