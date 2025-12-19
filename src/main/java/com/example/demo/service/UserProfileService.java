package com.example.demo.service;

import com.example.demo.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    public UserProfile save(UserProfile user) {
        return user;
    }

    public UserProfile findByUsername(String username) {
        return null;
    }
}
