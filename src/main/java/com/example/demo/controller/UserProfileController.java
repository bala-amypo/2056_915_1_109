package com.example.demo.controller;

import com.example.demo.service.UserProfileService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {
    private final UserProfileService userService;
    
    public UserProfileController(UserProfileService userService) {
        this.userService = userService;
    }
}
----------------------------------------------
