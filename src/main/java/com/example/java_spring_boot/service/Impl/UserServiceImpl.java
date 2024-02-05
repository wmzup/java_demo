package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.converter.UsersConverter;
import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dto.request.UserRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import com.example.java_spring_boot.mybatis.mapper.UsersMapper;
import com.example.java_spring_boot.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {

    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserRequest request) {
        // todo: check user is exist?
        UsersEntity entity = UsersConverter.toEntity(request);
        String encodePwd = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodePwd);
        entity.setCreateBy("Amanda");
        entity.setCreateDt(LocalDateTime.now());
        usersMapper.insert(entity);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> responses = new ArrayList<>();
        List<UsersEntity> usersEntities = usersMapper.findAll();

        if (usersEntities.size() <= 0) {
            return responses;
        }

        usersEntities.stream()
                .forEach(usersEntity -> {
                    responses.add(UsersConverter.toResponse(usersEntity));
                });

        return responses;
    }

    @Override
    public UserResponse getUser(String id) {
        UsersEntity entity = usersMapper.findById(id);
        return UsersConverter.toResponse(entity);
    }

    @Override
    public void updateUser(String id, UserRequest request) {
        UsersEntity entity = usersMapper.findById(id);
        String encodePwd = passwordEncoder.encode(request.password());
        entity.setPassword(encodePwd);
        entity.setAuthority(request.authority());
        entity.setLastModifiedBy("Amanda");
        entity.setLastModifiedDt(LocalDateTime.now());
        usersMapper.update(entity);
    }

    @Override
    public void deleteUser(String id) {
        usersMapper.delete(id);
    }
}
