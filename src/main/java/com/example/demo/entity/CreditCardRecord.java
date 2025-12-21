package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CreditCardRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String cardName;
    private String issuer;
    private String cardType;
    private Double annualFee;
    private String status;
    private LocalDateTime createdAt;

    public CreditCardRecord() {}

    public CreditCardRecord(Long userId, String cardName, String issuer, String cardType, Double annualFee, String status) {
        this.userId = userId;
        this.cardName = cardName;
        this.issuer = issuer;
        this.cardType = cardType;
        this.annualFee = annualFee;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    // ...
}
