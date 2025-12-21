package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;
    private String role;
    private Boolean active;

    private LocalDateTime createdAt;

    @ManyToMany
    private Set<CreditCardRecord> favouriteCards;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // getters & setters
}
