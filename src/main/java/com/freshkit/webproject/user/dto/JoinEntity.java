package com.freshkit.webproject.user.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinEntity {

    private String memberId;
    private String password;
    private String username;
    private String email;
    private String phone;

    private String role;      // role 추가
    // 추가적으로 생성자, getter/setter는 Lombok @Data가 자동으로 처리합니다.
}
