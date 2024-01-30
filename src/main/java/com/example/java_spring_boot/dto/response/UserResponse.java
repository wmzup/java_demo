package com.example.java_spring_boot.dto.response;

import com.example.java_spring_boot.enums.UserAuthorityEnum;

public record UserResponse(
        String id,
        String email,
        String password,
        UserAuthorityEnum authority
) {
}
