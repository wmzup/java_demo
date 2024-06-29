package com.example.java_spring_boot.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CreateUserResponse {
    private String id;
    private String name;
    private String job;
    private ZonedDateTime createdAt;
}
