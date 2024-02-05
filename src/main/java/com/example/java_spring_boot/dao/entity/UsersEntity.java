package com.example.java_spring_boot.dao.entity;

import com.example.java_spring_boot.enums.UserAuthorityEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsersEntity {
    private String id;
    private String email;
    private String password;
    private UserAuthorityEnum authority;
    private String createBy;
    private LocalDateTime createDt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDt;
}
