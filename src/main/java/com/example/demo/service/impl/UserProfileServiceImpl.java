package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserProfile createUser(UserProfile profile) {
        if (profile.getEmail() == null || profile.getUserId() == null) {
            throw new BadRequestException("Email and userId are required");
        }
        if (userProfileRepository.existsByEmail(profile.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        if (userProfileRepository.existsByUserId(profile.getUserId())) {
            throw new BadRequestException("UserId already in use");
        }
        if (profile.getPassword() != null) {
            profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        }
        if (profile.getActive() == null) {
            profile.setActive(true);
        }
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile u = getUserById(id);
        u.setActive(active);
        return userProfileRepository.save(u);
    }
}
