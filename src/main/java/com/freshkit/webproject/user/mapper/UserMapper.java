package com.freshkit.webproject.user.mapper;

import com.freshkit.webproject.user.dto.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    boolean existsByUsername(String memberId);
    void insertUser(UserEntity user);
    UserEntity findByUsername(String memberId);
    Optional<UserEntity> findByUserIdrole(String memberId);
}
