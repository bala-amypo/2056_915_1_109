
package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository repo;
    private final PasswordEncoder encoder;

    public UserProfileServiceImpl(UserProfileRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserProfile createUser(UserProfile p) {
        if (repo.existsByEmail(p.getEmail())) throw new BadRequestException("Email already exists");
        if (repo.existsByUserId(p.getUserId())) throw new BadRequestException("User ID already exists");
        
        p.setPassword(encoder.encode(p.getPassword()));
        if (p.getActive() == null) p.setActive(true);
        return repo.save(p);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByUserId(String userId) {
        return repo.findAll().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile user = getUserById(id);
        user.setActive(active);
        return repo.save(user);
    }
}