package com.freshkit.webproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private String memberId;
    private String pwd;
    private String name;
    private String email;
    private String phone;
    private String role;


}