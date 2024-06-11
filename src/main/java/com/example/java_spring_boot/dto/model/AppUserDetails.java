package com.example.java_spring_boot.dto.model;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.enums.UserAuthorityEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {

    private final UsersEntity usersEntity;

    public AppUserDetails(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    @Override
    public String getUsername() {
        return usersEntity.getEmail();
    }

    @Override
    public String getPassword() {
        return usersEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usersEntity.getAuthority();
    }

    @Override
    public boolean isEnabled() {
        return usersEntity.isEnabled();
    }

    // 新增自定義的部分
    public int getId() {
        return usersEntity.getId();
    }

    public List<UserAuthorityEnum> getUserAuthority() {
        return usersEntity.getAuthority();
    }

    public boolean getIsPremium() {
        return usersEntity.isPremium();
    }

    public LocalDateTime getTrailExpiration() {
        return usersEntity.getTrailExpiration();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (usersEntity.getTrailExpiration() == null) {
            return true;
        }

        return LocalDateTime.now().isBefore(usersEntity.getTrailExpiration());
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
