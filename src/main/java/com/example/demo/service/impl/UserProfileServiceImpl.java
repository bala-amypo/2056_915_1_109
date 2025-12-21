package com.example.demo.service.impl;

import com.example.demo.entity.UserProfileRecord;
import com.example.demo.repository.UserProfileRecordRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRecordRepository repository;

    public UserProfileServiceImpl(UserProfileRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserProfileService createUser(UserProfileService user) {
        return repository.save(user);
    }

    @Override
    public UserProfileService getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserProfileService> getAllUsers() {
        return repository.findAll();
    }
}
