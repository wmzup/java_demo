package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dto.model.AppUserDetails;
import com.example.java_spring_boot.enums.UserAuthorityEnum;
import com.example.java_spring_boot.mybatis.mapper.UsersMapper;
import com.example.java_spring_boot.service.UsersService;
import com.example.java_spring_boot.util.UserDetailsService.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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
        usersEntity.setAuthority(UserAuthorityEnum.ADMIN);

        when(usersMapper.findByEmail(email)).thenReturn(usersEntity);

        AppUserDetails appUserDetails = (AppUserDetails) userDetailsService.loadUserByUsername(email);

        Assert.assertEquals(usersEntity.getId(), appUserDetails.getId());
        Assert.assertEquals(usersEntity.getEmail(), appUserDetails.getUsername());
        Assert.assertEquals(usersEntity.getAuthority(), appUserDetails.getUserAuthority());
    }
}
