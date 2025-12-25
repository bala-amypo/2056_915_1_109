package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;

import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    
    private final UserProfileService userService;
    private final UserProfileRepository userRepository;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(UserProfileService userService, UserProfileRepository userRepository,
                         AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }
    
    public ResponseEntity<JwtResponse> register(RegisterRequest request) {
        UserProfile user = new UserProfile();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setActive(true);
        
        UserProfile saved = userService.createUser(user);
        
        String token = "jwt-token-" + saved.getId();
        
        JwtResponse response = new JwtResponse();
        response.setToken(token);
        response.setUserId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole());
        
        return ResponseEntity.ok(response);
    }
    
    public ResponseEntity<JwtResponse> login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserProfile user = userRepository.findByEmail(request.getEmail()).orElse(null);
        
        String token = "jwt-token-" + user.getId();
        
        JwtResponse response = new JwtResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        
        return ResponseEntity.ok(response);
    }
}