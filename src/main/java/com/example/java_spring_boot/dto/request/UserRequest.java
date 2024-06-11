package com.example.java_spring_boot.dto.request;

import com.example.java_spring_boot.enums.UserAuthorityEnum;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserRequest(
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        List<UserAuthorityEnum> authority,
        @NotNull
        boolean isEnabled,
        @NotNull
        boolean isPremium
) {
}
