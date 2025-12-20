package com.example.demo.service;

import com.example.demo.entity.UserProfile;

public interface UserProfileService {

    UserProfile register(UserProfile user);

    UserProfile findByEmail(String email);
}