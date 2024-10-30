package com.freshkit.webproject.user.service;

import com.freshkit.webproject.user.dto.JoinEntity;
import com.freshkit.webproject.user.dto.UserEntity;
import com.freshkit.webproject.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JoinService(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 회원가입 처리를 합니다.
    public void joinProcess(JoinEntity joinEntity) {
        String memberId = joinEntity.getMemberId();
        String pwd = joinEntity.getPassword();

        Boolean isExist = userMapper.existsByUsername(memberId);

        if (isExist) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(pwd);

        UserEntity data = new UserEntity();
        data.setMemberId(memberId);
        data.setPwd(encodedPassword);
        data.setName(joinEntity.getUsername());
        data.setEmail(joinEntity.getEmail());
        data.setPhone(joinEntity.getPhone());
        data.setRole("ROLE_USER");

        userMapper.insertUser(data);
        System.out.println("회원 가입을 진행합니다.");
    }

    // 아이디 존재 여부를 확인합니다.
    public boolean checkMemberIdExists(String memberId) {
        return userMapper.existsByUsername(memberId);
    }
}
