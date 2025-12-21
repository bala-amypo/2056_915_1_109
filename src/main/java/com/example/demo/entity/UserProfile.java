package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;

    private Boolean active = true;

    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "user_favorite_cards",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<CreditCardRecord> favoriteCards = new HashSet<>();

    public UserProfile() {}
    
    public UserProfile(String userId, String fullName, String email, String password, String role, Boolean active) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    // ...
}
