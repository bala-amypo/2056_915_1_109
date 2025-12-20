package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PurchaseIntentRecord {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Double amount;
    private String category;
    private String merchant;
    private LocalDateTime intentDate;

    // getters & setters
}