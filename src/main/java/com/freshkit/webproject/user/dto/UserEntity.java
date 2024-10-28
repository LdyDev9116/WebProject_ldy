package com.freshkit.webproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private String memberId;
    private String pwd;
    private String name;
    private String email;
    private String phone;
    private String role;

    // Lombok이 이미 @AllArgsConstructor와 @NoArgsConstructor로 생성자를 생성합니다.
}
