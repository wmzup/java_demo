package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.UserCreateRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {
    void createUser(UserCreateRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUser(String id);
}
