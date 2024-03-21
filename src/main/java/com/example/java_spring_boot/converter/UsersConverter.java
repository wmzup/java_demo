package com.example.java_spring_boot.converter;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dto.request.UserRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UsersConverter {

    public static UsersEntity toEntity(UserRequest request) {
        UsersEntity entity = new UsersEntity();
        entity.setEmail(request.email());
        entity.setPassword(request.password());
        entity.setAuthority(request.authority());
        entity.setEnabled(request.isEnabled());
        entity.setPremium(request.isPremium());
        entity.setCreateDt(LocalDateTime.now());
        return entity;
    }

    public static UserResponse toResponse(UsersEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAuthority(),
                entity.getCreateBy(),
                entity.getCreateDt(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDt()
        );
    }

}