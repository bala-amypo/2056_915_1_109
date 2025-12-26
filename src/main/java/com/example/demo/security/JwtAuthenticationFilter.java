
package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                // Token is valid, proceed with request
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
----------------
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