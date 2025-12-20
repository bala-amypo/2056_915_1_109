package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserProfileService userService;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserProfileService userService,
                          UserProfileRepository userProfileRepository,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
        if (userProfileRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        String userId = request.getUserId();
        if (userId == null || userId.isBlank()) {
            userId = "U" + System.currentTimeMillis();
        }
        if (userProfileRepository.existsByUserId(userId)) {
            throw new BadRequestException("UserId already in use");
        }

        UserProfile profile = new UserProfile();
        profile.setFullName(request.getFullName());
        profile.setEmail(request.getEmail());
        profile.setPassword(request.getPassword());
        profile.setRole(request.getRole());
        profile.setUserId(userId);
        profile.setActive(true);

        UserProfile saved = userService.createUser(profile);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        JwtResponse resp = new JwtResponse(token, saved.getId(), saved.getEmail(), saved.getRole());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        if (auth == null) {
            throw new BadRequestException("Authentication failed");
        }

        UserProfile user = userProfileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (user.getActive() != null && !user.getActive()) {
            throw new BadRequestException("User inactive");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        JwtResponse resp = new JwtResponse(token, user.getId(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(resp);
    }
}
