package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.converter.UsersConverter;
import com.example.java_spring_boot.dao.entity.UsersEntity;
import com.example.java_spring_boot.dao.repository.UsersRepository;
import com.example.java_spring_boot.dto.request.UserCreateRequest;
import com.example.java_spring_boot.dto.response.UserResponse;
import com.example.java_spring_boot.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserCreateRequest request) {
        UsersEntity entity = UsersConverter.toEntity(request);
        String encodePwd = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodePwd);
        usersRepository.insert(entity);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> responses = new ArrayList<>();
        List<UsersEntity> usersEntities = usersRepository.findAll();

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

        Optional<UsersEntity> usersOpt = usersRepository.findById(id);

        if (!usersOpt.isPresent()) {
            return null;
        }

        UsersEntity entity = usersOpt.get();

        return UsersConverter.toResponse(entity);
    }
}
