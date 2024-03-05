package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.LoginRequest;
import com.example.java_spring_boot.dto.response.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface LoginService {
    LoginResponse createToken(LoginRequest request);

    Map<String, Object> parseToken(String token);
}
