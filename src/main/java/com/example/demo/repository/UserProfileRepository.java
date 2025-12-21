package com.example.demo.repository;

import com.example.demo.entity.UserProfileRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRecordRepository
        extends JpaRepository<UserProfileRecord, Long> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    Optional<UserProfile> findByEmail(String email);

    Optional<UserProfile> findByUserId(String userId);
}
