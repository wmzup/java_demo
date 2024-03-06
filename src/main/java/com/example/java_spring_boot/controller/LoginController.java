package com.example.java_spring_boot.controller;

import com.example.java_spring_boot.dto.request.LoginRequest;
import com.example.java_spring_boot.dto.response.LoginResponse;
import com.example.java_spring_boot.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        LoginResponse response = loginService.createToken(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/parseToken")
    public ResponseEntity<Map<String, Object>> parseToken(
            @RequestHeader String authorization
    ) {
        Map<String, Object> jwtPayload = loginService.parseToken(authorization);
        return ResponseEntity.ok(jwtPayload);
    }
}
