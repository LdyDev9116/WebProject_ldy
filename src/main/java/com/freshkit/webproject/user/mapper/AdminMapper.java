package com.freshkit.webproject.user.mapper;

import com.freshkit.webproject.user.dto.AdminDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<AdminDto> getAllUsers();

    AdminDto getUserById(String memberId);

    void insertUser(AdminDto user);

    void updateUser(AdminDto user);

    void deleteUser(String memberId);
}
