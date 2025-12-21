package com.example.demo.dto;

import java.time.LocalDateTime;

public class PurchaseIntentDto {
    private Long id;
    private Long userId;
    private Double amount;
    private String category;
    private String merchant;
    private LocalDateTime intentDate;

    // Default constructor (for JSON deserialization)
    public PurchaseIntentDto() {}

    // Parameterized constructor (for creating from entity)
    public PurchaseIntentDto(Long id, Long userId, Double amount, String category, 
                           String merchant, LocalDateTime intentDate) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.merchant = merchant;
        this.intentDate = intentDate;
    }

    // Constructor from PurchaseIntentRecord entity
    public PurchaseIntentDto(com.example.demo.entity.PurchaseIntentRecord intent) {
        this.id = intent.getId();
        this.userId = intent.getUserId();
        this.amount = intent.getAmount();
        this.category = intent.getCategory();
        this.merchant = intent.getMerchant();
        this.intentDate = intent.getIntentDate();
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public LocalDateTime getIntentDate() {
        return intentDate;
    }

    public void setIntentDate(LocalDateTime intentDate) {
        this.intentDate = intentDate;
    }
}
