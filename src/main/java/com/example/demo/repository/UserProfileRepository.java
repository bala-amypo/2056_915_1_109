package com.example.demo.repository;

import com.example.demo.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    UserProfile findByEmail(String email);
    UserProfile findByUserId(String userId);
}
