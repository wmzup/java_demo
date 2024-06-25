package com.example.java_spring_boot.dto.response;

import com.example.java_spring_boot.enums.UserAuthorityEnum;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        int id,
        String email,
        String password,
        UserAuthorityEnum authority,
        String createBy,
        LocalDateTime createDt,
        String lastModifiedBy,
        LocalDateTime lastModifiedDt
) {
}
