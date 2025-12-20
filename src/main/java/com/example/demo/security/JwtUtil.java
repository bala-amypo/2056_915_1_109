package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JwtUtil {

    private final byte[] secret;
    private final Long expirationMs;

    public JwtUtil(byte[] secret, Long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId, String email, String role) {
        long now = System.currentTimeMillis();
        long exp = now + expirationMs;
        String payload = userId + ":" + email + ":" + role + ":" + exp;
        String base = Base64.getEncoder()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        // very simple "signature"
        String sig = Base64.getEncoder()
                .encodeToString((base + new String(secret, StandardCharsets.UTF_8))
                        .getBytes(StandardCharsets.UTF_8));
        return base + "." + sig;
    }

    private String getPayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 2) return null;
        try {
            byte[] decoded = Base64.getDecoder().decode(parts[0]);
            return new String(decoded, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String extractEmail(String token) {
        String payload = getPayload(token);
        if (payload == null) return null;
        String[] pieces = payload.split(":");
        return pieces.length >= 2 ? pieces[1] : null;
    }

    public String extractRole(String token) {
        String payload = getPayload(token);
        if (payload == null) return null;
        String[] pieces = payload.split(":");
        return pieces.length >= 3 ? pieces[2] : null;
    }

    public Long extractUserId(String token) {
        String payload = getPayload(token);
        if (payload == null) return null;
        String[] pieces = payload.split(":");
        try {
            return Long.valueOf(pieces[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        String payload = getPayload(token);
        if (payload == null) return false;
        String[] pieces = payload.split(":");
        if (pieces.length < 4) return false;
        try {
            long exp = Long.parseLong(pieces[3]);
            return exp >= System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }
}
