package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfile> register(@Valid @RequestBody UserProfile user) {
        return ResponseEntity.ok(userProfileService.register(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserProfile> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userProfileService.findByEmail(email));
    }
}
