package com.example.java_spring_boot.dao.entity;

import com.example.java_spring_boot.enums.UserAuthorityEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "users")
public class UsersEntity {
    private String id;
    private String email;
    private String password;
    private UserAuthorityEnum authority;
}
