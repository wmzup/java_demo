package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.UserRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {
    void createUser(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUser(String id);
    void updateUser(String id, UserRequest request);
    void deleteUser(String id);
}
