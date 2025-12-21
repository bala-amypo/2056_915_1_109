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

    @PrePersist
    public void onCreate() {
        this.intentDate = LocalDateTime.now();
    }

    // getters & setters
}
