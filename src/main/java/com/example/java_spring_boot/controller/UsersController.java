package com.example.java_spring_boot.controller;

import com.example.java_spring_boot.dto.request.UserRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import com.example.java_spring_boot.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            UserRequest request
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

    @PatchMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(
            @PathVariable
            String id,
            @RequestBody
            @Valid
            UserRequest request
    ) {
        usersService.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(
            @PathVariable
            String id
    ) {
        String result = usersService.deleteUser(id);
        if (result.equals("success")) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
