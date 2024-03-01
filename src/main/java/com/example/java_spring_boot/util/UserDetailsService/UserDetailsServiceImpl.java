package com.example.java_spring_boot.util.UserDetailsService;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.mybatis.mapper.UsersMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersMapper usersMapper;

    public UserDetailsServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        UsersEntity usersEntity = usersMapper.findByEmail(useremail);
        if (usersEntity == null) {
            throw new UsernameNotFoundException("User isn't exist: " + useremail);
        }

        return new User(useremail, usersEntity.getPassword(), List.of(usersEntity.getAuthority()));
    }
}
