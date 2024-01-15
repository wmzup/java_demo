package com.example.java_spring_boot.dto.response;

public record UserResponse(
        String id,
        String email,
        String password,
        String authority
) {
}
