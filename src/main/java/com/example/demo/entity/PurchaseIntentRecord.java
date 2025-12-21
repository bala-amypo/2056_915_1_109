package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PurchaseIntentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Double amount;
    private String category;
    private String merchant;
    private LocalDateTime intentDate;

    public PurchaseIntentRecord() {}

    public PurchaseIntentRecord(Long userId, Double amount, String category, String merchant) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.merchant = merchant;
        this.intentDate = LocalDateTime.now();
    }

    // Getters & Setters
    // ...
}
