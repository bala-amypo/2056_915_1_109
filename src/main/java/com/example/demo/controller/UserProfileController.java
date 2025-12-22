package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public UserProfile createUser(@RequestBody UserProfile user) {
        return userProfileService.createUser(user);
    }

    @GetMapping("/{id}")
    public UserProfile getUserById(@PathVariable Long id) {
        return userProfileService.getUserById(id);
    }

    @GetMapping
    public List<UserProfile> getAllUsers() {
        return userProfileService.getAllUsers();
    }
}
