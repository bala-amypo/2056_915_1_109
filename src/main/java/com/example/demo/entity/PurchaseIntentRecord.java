package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchase_intent")
public class PurchaseIntentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String category;
    private Double budget;

    public PurchaseIntentRecord() {
    }

    public PurchaseIntentRecord(Long userId, String category, Double budget) {
        this.userId = userId;
        this.category = category;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public Double getBudget() {
        return budget;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
