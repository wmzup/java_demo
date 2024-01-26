package com.example.java_spring_boot.dto.request;

import com.example.java_spring_boot.enums.UserAuthorityEnum;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotNull
        String email,

        @NotNull
        String passwrod,

        @NotNull
        UserAuthorityEnum authority
) {
}
