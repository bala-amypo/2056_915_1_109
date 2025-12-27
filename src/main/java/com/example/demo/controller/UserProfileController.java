package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    private final UserProfileService service;
    public UserProfileController(UserProfileService service) { this.service = service; }

    @PostMapping("/register")
    public UserProfile register(@RequestBody UserProfile profile) { return service.createUser(profile); }

    @GetMapping("/{id}")
    public UserProfile get(@PathVariable Long id) { return service.getUserById(id); }

    @GetMapping
    public List<UserProfile> list() { return service.getAllUsers(); }

    @PutMapping("/{id}/status")
    public UserProfile updateStatus(@PathVariable Long id, @RequestParam boolean active) { return service.updateUserStatus(id, active); }

    @GetMapping("/lookup/{userId}")
    public UserProfile lookup(@PathVariable String userId) { return service.findByUserId(userId); }
}//UserProfileController