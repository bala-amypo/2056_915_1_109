package com.example.demo.security;

import java.util.Base64;

public class JwtUtil {
    private final byte[] secret;
    private final Long expirationMs;

    public JwtUtil(byte[] secret, Long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId, String email, String role) {
        String payload = userId + ":" + email + ":" + role;
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

    public String extractEmail(String token) {
        String[] parts = decodeToken(token);
        return parts.length > 1 ? parts[1] : null;
    }

    public String extractRole(String token) {
        String[] parts = decodeToken(token);
        return parts.length > 2 ? parts[2] : null;
    }

    public Long extractUserId(String token) {
        String[] parts = decodeToken(token);
        return parts.length > 0 ? Long.valueOf(parts[0]) : null;
    }

    public boolean validateToken(String token) {
        try {
            decodeToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String[] decodeToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split(":");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}