package com.example.demo.security;

import com.example.demo.service.UserProfileService;
import com.example.demo.entity.UserProfile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserProfileService userProfileService;

    public CustomUserDetailsService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserProfile userProfile = userProfileService.findByEmail(username);
            return User.builder()
                .username(userProfile.getEmail())
                .password(userProfile.getPassword())
                .authorities(new ArrayList<>())
                .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}