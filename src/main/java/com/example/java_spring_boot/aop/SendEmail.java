package com.example.java_spring_boot.aop;

import com.example.java_spring_boot.enums.ActionType;
import com.example.java_spring_boot.enums.EntityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SendEmail {
    EntityType entityType();
    ActionType actionType();
    int idParamIdentity() default -1;
}
