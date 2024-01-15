package com.example.java_spring_boot.dao.repository;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<UsersEntity, String> {
}
