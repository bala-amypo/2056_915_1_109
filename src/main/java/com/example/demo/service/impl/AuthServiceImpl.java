package com.example.demo.service.impl;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserProfileService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserProfileService userProfileService;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // EXACT constructor order from spec: UserProfileService, UserProfileRepository, AuthenticationManager, JwtUtil
    public AuthServiceImpl(UserProfileService userProfileService,
                           UserProfileRepository userProfileRepository,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {
        this.userProfileService = userProfileService;
        this.userProfileRepository = userProfileRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public JwtResponse register(RegisterRequest request) {
        if (userProfileRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        
        String userId = request.getUserId();
        if (userId == null || userId.trim().isEmpty()) {
            userId = "U" + System.currentTimeMillis();
        }
        
        if (userProfileRepository.existsByUserId(userId)) {
            throw new BadRequestException("UserId already in use");
        }

        UserProfile profile = new UserProfile();
        profile.setFullName(request.getFullName());
        profile.setEmail(request.getEmail());
        profile.setPassword(request.getPassword());
        profile.setRole(request.getRole() != null ? request.getRole() : "USER");
        profile.setUserId(userId);
        profile.setActive(true);

        UserProfile saved = userProfileService.createUser(profile);
        
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        return new JwtResponse(token, saved.getId(), saved.getEmail(), saved.getRole());
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            throw new BadRequestException("Invalid credentials");
        }

        UserProfile user = userProfileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new BadRequestException("User account is deactivated");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return new JwtResponse(token, user.getId(), user.getEmail(), user.getRole());
    }
}
