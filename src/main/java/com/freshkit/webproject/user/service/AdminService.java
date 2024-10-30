package com.freshkit.webproject.user.service;

import com.freshkit.webproject.user.dto.AdminDto;
import com.freshkit.webproject.user.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public List<AdminDto> getAllUsers() {
        return adminMapper.getAllUsers();
    }

    public void saveOrUpdateUser(AdminDto user) {
        if (adminMapper.getUserById(user.getMemberId()) == null) {
            adminMapper.insertUser(user);
        } else {
            adminMapper.updateUser(user);
        }
    }

    public AdminDto getUserById(String memberId) {
        return adminMapper.getUserById(memberId);
    }

    public void deleteUser(String memberId) {
        adminMapper.deleteUser(memberId);
    }
}
