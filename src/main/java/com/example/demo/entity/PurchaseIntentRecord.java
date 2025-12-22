package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_intents")
public class PurchaseIntentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double amount;

    private String category;

    private String merchant;

    private LocalDateTime intentDate;

    public PurchaseIntentRecord() {
    }

    public PurchaseIntentRecord(Long id, Long userId, Double amount,
                                String category, String merchant) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.merchant = merchant;
    }

    @PrePersist
    public void onCreate() {
        this.intentDate = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getMerchant() {
        return merchant;
    }

    public LocalDateTime getIntentDate() {
        return intentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
}
