package com.example.java_spring_boot.enums;

public enum EntityType {
    PRODUCT("product"), USER("user");

    private String entityType;

    EntityType(String entityType) {
        this.entityType = entityType;
    }

    @Override
    public String toString() {
        return entityType;
    }
}
