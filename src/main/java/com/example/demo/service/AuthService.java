

package com.example.demo.service.impl;

import com.example.demo.service.AuthService;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;

public class AuthServiceImpl implements AuthService {
    
    @Override
    public JwtResponse register(RegisterRequest request) {
        // Implementation would be in AuthController
        return null;
    }
    
    @Override
    public JwtResponse login(LoginRequest request) {
        // Implementation would be in AuthController
        return null;
    }
}

-----------------------------------------------------------
service:
package com.example.demo.service;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;

public interface AuthService {
    JwtResponse register(RegisterRequest request);
    JwtResponse login(LoginRequest request);
}
