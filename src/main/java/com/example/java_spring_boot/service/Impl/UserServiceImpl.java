package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.converter.UsersConverter;
import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dto.request.UserRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import com.example.java_spring_boot.enums.UserAuthorityEnum;
import com.example.java_spring_boot.mybatis.mapper.UsersMapper;
import com.example.java_spring_boot.service.UsersService;
import com.example.java_spring_boot.util.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.java_spring_boot.enums.UserAuthorityEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {

    private final UsersMapper usersMapper;
    private final UserIdentity userIdentity;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserRequest request) {
        UsersEntity entity = UsersConverter.toEntity(request);
        String encodePwd = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodePwd);
        if (userIdentity.isPremium()) {
            entity.setCreateBy(userIdentity.getUsername());
        }
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
    public String deleteUser(String id) {
        UsersEntity usersEntity = usersMapper.findById(id);
        int userId = usersEntity.getId();
        String createBy = usersEntity.getCreateBy();
        UserAuthorityEnum authority = userIdentity.getUserAuthority();

        if (userId == userIdentity.getId() ||
            createBy.equals(userIdentity.getUsername()) ||
            authority.equals(ADMIN)) {
            usersMapper.delete(id);
            return "success";
        } else {
            return "fail";
        }
    }
}
