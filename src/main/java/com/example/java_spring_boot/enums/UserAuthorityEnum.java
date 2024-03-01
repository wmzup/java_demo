package com.example.java_spring_boot.enums;


import org.springframework.security.core.GrantedAuthority;

public enum UserAuthorityEnum implements GrantedAuthority {
    ADMIN, NORMAL, GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}

