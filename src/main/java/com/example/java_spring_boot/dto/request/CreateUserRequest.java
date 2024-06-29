package com.example.java_spring_boot.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String name;
    private String job;

    public static CreateUserRequest of(String name, String job) {
        CreateUserRequest request = new CreateUserRequest();
        request.name = name;
        request.job = job;
        return request;
    }
}
