package com.example.java_spring_boot.dto.request;

import com.example.java_spring_boot.enums.UserAuthorityEnum;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        UserAuthorityEnum authority,
        @NotNull
        boolean isEnabled,
        @NotNull
        boolean isPremium
) {
}
