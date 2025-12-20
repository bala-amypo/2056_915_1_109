package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
@Override
public UserProfile findByEmail(String email) {
    return userProfileRepository.findByEmail(email)
            .orElse(null);
}

public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repo;
    private final PasswordEncoder encoder;

    public UserProfileServiceImpl(UserProfileRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public UserProfile createUser(UserProfile u) {
        if (repo.existsByEmail(u.getEmail()))
            throw new BadRequestException("Email exists");
        if (repo.existsByUserId(u.getUserId()))
            throw new BadRequestException("UserId exists");

        u.setPassword(encoder.encode(u.getPassword()));
        return repo.save(u);
    }

    public UserProfile getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<UserProfile> getAllUsers() {
        return repo.findAll();
    }

    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile u = getUserById(id);
        u.setActive(active);
        return repo.save(u);
    }
}