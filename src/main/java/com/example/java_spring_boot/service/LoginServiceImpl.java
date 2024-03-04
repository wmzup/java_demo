package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.LoginRequest;
import com.example.java_spring_boot.dto.response.LoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private Key secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    private void init() {
        String key = "AmandaRunningSpringBootLearner";
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    }

    @Override
    public LoginResponse createToken(LoginRequest request) {
        String accessToken = createAccessToken(request.getUsername());
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        return response;
    }

    public String createAccessToken(String username) {

        // 有效時間
        long expirationMillis = Instant.now()
                .plusSeconds(90)
                .getEpochSecond() * 1000;

        // 設置標準內容與自定義內容
        Claims claims = Jwts.claims();
        claims.setSubject("Access Token");  // 主旨
        claims.setIssuedAt(new Date());     // 核發時間
        claims.setExpiration(new Date(expirationMillis));   // 到期時間
        claims.put("username", username);   // username作為自定義內容放入JWT，簽名後就產生出來了

        // 簽名後產生token
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }
}
