package com.example.java_spring_boot.mybatis.mapper;

import com.example.java_spring_boot.dao.entity.UsersEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {
    List<UsersEntity> findAll();
    UsersEntity findById(String id);
    // insert, update, delete語句是不需要返回值的，它們都是默認返回一個int
    int insert(UsersEntity users);
    int update(UsersEntity users);
    int delete(String id);
}
