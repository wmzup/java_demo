package com.example.java_spring_boot.util;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dto.model.AppUserDetails;
import com.example.java_spring_boot.enums.UserAuthorityEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserIdentity {

    private final AppUserDetails EMPTY_USER = new AppUserDetails(new UsersEntity());

    private AppUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return "anonymousUser".equals(principal)
                ? EMPTY_USER
                : (AppUserDetails) principal;
    }

    public boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return "anonymousUser".equals(principal);
    }

    public int getId() {
        return getUserDetails().getId();
    }

    public String getUsername() {
        return getUserDetails().getUsername();
    }

    public UserAuthorityEnum getUserAuthority() {
        return getUserDetails().getUserAuthority();
    }

    public boolean isPremium() {
        return getUserDetails().getIsPremium();
    }
}
