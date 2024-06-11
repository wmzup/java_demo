package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dto.model.AppUserDetails;
import com.example.java_spring_boot.enums.UserAuthorityEnum;
import com.example.java_spring_boot.mybatis.mapper.UsersMapper;
import com.example.java_spring_boot.service.UsersService;
import com.example.java_spring_boot.util.UserDetailsService.UserDetailsServiceImpl;
import com.example.java_spring_boot.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UsersMapper usersMapper;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByUsername() {
        String email = "abc@gmail.com";
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(123);
        usersEntity.setEmail(email);
        usersEntity.setAuthority(List.of(UserAuthorityEnum.ADMIN));

        when(usersMapper.findByEmail(email)).thenReturn(usersEntity);

        AppUserDetails appUserDetails = (AppUserDetails) userDetailsService.loadUserByUsername(email);

        Assert.assertEquals(usersEntity.getId(), appUserDetails.getId());
        Assert.assertEquals(usersEntity.getEmail(), appUserDetails.getUsername());
        Assert.assertEquals(usersEntity.getAuthority(), appUserDetails.getUserAuthority());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_userNotFoundException() {
        when(usersService.getUserByEmail(anyString())).thenThrow(new NotFoundException("User not found"));
        userDetailsService.loadUserByUsername("abc@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAuthorityAsEmptyArray() {
        UsersEntity usersEntity = mock(UsersEntity.class);
        doThrow(new IllegalArgumentException("Authority is empty")).when(usersEntity).setAuthority(Collections.emptyList());

        usersEntity.setAuthority(new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAuthorityAsNull() {
        UsersEntity usersEntity = mock(UsersEntity.class);
        doThrow(new IllegalArgumentException("Authority is null")).when(usersEntity).setAuthority(null);

        usersEntity.setAuthority(null);
    }

}
