package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile register(UserProfile user) {
        user.setActive(true);
        return userProfileRepository.save(user);
    }

    @Override
    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByEmail(email).orElse(null);
    }
}