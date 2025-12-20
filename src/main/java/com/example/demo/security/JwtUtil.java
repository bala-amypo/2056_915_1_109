package com.example.demo.security;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {

    private final byte[] secret;
    private final Long expiration;

    public JwtUtil(byte[] secret, Long expirationMs) {
        this.secret = secret;
        this.expiration = expirationMs;
    }

    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
    }

    private Claims claims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return claims(token).get("email", String.class);
    }

    public String extractRole(String token) {
        return claims(token).get("role", String.class);
    }

    public Long extractUserId(String token) {
        return claims(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            claims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}