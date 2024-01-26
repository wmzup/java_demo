package com.example.java_spring_boot.enums;


import java.util.HashMap;
import java.util.Map;

public enum UserAuthorityEnum {
    ADMIN("admin"),
    NORMAL("normal"),
    GUEST("guest");

    private static final Map<String, UserAuthorityEnum> authorityMap = new HashMap<>();

    static {
        for (UserAuthorityEnum authorityEnum : UserAuthorityEnum.values()) {
            authorityMap.put(authorityEnum.getAuthority().toUpperCase(), authorityEnum);
        }
    }

    private String authority;

    private UserAuthorityEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

}

