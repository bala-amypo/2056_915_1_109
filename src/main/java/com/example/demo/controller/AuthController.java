package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserProfileService userService;
    private final UserProfileRepository userRepo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserProfileService userService, UserProfileRepository userRepo, 
                          AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest req) {
        UserProfile u = new UserProfile();
        u.setEmail(req.getEmail());
        u.setFullName(req.getFullName());
        u.setPassword(req.getPassword());
        u.setUserId(java.util.UUID.randomUUID().toString());
        u.setRole(req.getRole());
        UserProfile saved = userService.createUser(u);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        return ResponseEntity.ok(new JwtResponse(token, saved.getId(), saved.getEmail(), saved.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        UserProfile u = userRepo.findByEmail(req.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(u.getId(), u.getEmail(), u.getRole());
        return ResponseEntity.ok(new JwtResponse(token, u.getId(), u.getEmail(), u.getRole()));
    }
}
//authcontroller