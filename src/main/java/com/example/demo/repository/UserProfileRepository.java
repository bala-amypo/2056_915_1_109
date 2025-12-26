
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.UserProfile;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    Optional<UserProfile> findByEmail(String email);
}
