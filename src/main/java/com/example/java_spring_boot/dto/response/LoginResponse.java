package com.example.java_spring_boot.dto.response;

import com.example.java_spring_boot.enums.UserAuthorityEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private int userId;
    private String email;
    private UserAuthorityEnum userAuthority;
    private boolean premium;
    private LocalDateTime trailExpiration;
}
