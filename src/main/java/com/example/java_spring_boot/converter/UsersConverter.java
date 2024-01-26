package com.example.java_spring_boot.converter;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dto.request.UserCreateRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import com.example.java_spring_boot.enums.UserAuthorityEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsersConverter {

    public static UsersEntity toEntity(UserCreateRequest request) {
        UsersEntity entity = new UsersEntity();
        entity.setEmail(request.email());
        entity.setPassword(request.passwrod());
        entity.setAuthority(request.authority());
        return entity;
    }

    public static UserResponse toResponse(UsersEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAuthority()
        );
    }

}