package com.example.demo.config;

import com.example.demo.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {
    
    @Bean
    public JwtUtil jwtUtil() {
        byte[] secret = "credit-card-reward-maximizer-jwt-secret-key-2024".getBytes(StandardCharsets.UTF_8);
        return new JwtUtil(secret, 24L * 60L * 60L * 1000L); // 24 hours
    }
}
