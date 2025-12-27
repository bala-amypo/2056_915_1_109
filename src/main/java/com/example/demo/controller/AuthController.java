package com.example.demo.controller;

import com.example.demo.service.UserProfileService;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.dto.*;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserProfileService userService;
    private final UserProfileRepository userRepository;
    private final Object authenticationManager; 
    private final JwtUtil jwtUtil;
    
    public AuthController(UserProfileService userService, UserProfileRepository userRepository, 
                         Object authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        
        String userId = request.getUserId() != null ? request.getUserId() : UUID.randomUUID().toString();
        if (userRepository.existsByUserId(userId)) {
            throw new BadRequestException("User ID already exists");
        }
        
        UserProfile user = new UserProfile();
        user.setUserId(userId);
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        user.setActive(true);
        
        UserProfile saved = userService.createUser(user);
        
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        
        JwtResponse response = new JwtResponse();
        response.setToken(token);
        response.setUserId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        UserProfile user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadRequestException("User not found"));
            
        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new BadRequestException("User account is inactive");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        
        JwtResponse response = new JwtResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        
        return ResponseEntity.ok(response);
    }
}
