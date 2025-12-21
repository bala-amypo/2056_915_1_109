package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userService;

    public UserProfileController(UserProfileService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserProfile createUser(@RequestBody UserProfile profile) {
        return userService.createUser(profile);
    }

    @GetMapping("/{id}")
    public UserProfile getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserProfile> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/status")
    public UserProfile updateStatus(@PathVariable Long id,
                                    @RequestParam boolean active) {
        return userService.updateUserStatus(id, active);
    }
}
