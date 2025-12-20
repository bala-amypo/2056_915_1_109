package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private Boolean active;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) this.role = "USER";
    }

    // getters & setters
    // (ALL required – omitted here for brevity, but MUST be present)
}