package com.example.demo.service;

import com.example.demo.entity.UserProfile;
import java.util.List;

public interface UserProfileService {
    UserProfile createUser(UserProfile profile);
    UserProfile getUserById(Long id);
    UserProfile findByEmail(String email);
    List<UserProfile> getAllUsers();
    UserProfile updateUserStatus(Long id, boolean active);
}
