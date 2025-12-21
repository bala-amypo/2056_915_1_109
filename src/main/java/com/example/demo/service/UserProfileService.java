package com.example.demo.service;

import com.example.demo.entity.UserProfileRecord;
import java.util.List;

public interface UserProfileService {

    UserProfileRecord createUser(UserProfileRecord user);

    UserProfileRecord getUserById(Long id);

    List<UserProfileRecord> getAllUsers();
}
