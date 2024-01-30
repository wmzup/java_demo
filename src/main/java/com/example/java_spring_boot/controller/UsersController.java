package com.example.java_spring_boot.controller;

import com.example.java_spring_boot.dto.request.UserCreateRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import com.example.java_spring_boot.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(
            @RequestBody
            @Valid
            UserCreateRequest request
    ) {
        usersService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> usersList = usersService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(
            @PathVariable
            String id
    ) {
        UserResponse user = usersService.getUser(id);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }
}
