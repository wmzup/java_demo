package com.example.java_spring_boot.controller;

import com.example.java_spring_boot.dto.request.LoginRequest;
import com.example.java_spring_boot.dto.response.LoginResponse;
import com.example.java_spring_boot.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
