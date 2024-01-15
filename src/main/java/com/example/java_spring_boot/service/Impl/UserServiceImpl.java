package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dto.request.UserCreateRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import com.example.java_spring_boot.service.UsersService;

import java.util.List;

public class UserServiceImpl implements UsersService {
    @Override
    public void createUser(UserCreateRequest request) {

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return null;
    }

    @Override
    public UserResponse getUser(String id) {
        return null;
    }
}
