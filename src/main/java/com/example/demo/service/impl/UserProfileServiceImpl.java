package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;

import java.util.List;

public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile createUser(UserProfile profile) {
        if (userProfileRepository.existsByUserId(profile.getUserId())) {
            throw new RuntimeException("UserId already exists");
        }
        if (userProfileRepository.existsByEmail(profile.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        profile.setActive(true);
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserProfile findByUserId(String userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile user = getUserById(id);
        user.setActive(active);
        return userProfileRepository.save(user);
    }
}
