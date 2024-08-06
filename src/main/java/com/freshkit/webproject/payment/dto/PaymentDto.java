package com.freshkit.webproject.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private String impUid;
    private String merchantUid;
    private String name;
    private String phone;
    private String email;
    private String postcode;
    private String address;
    private String detailAddress;
    private int amount;
    private int pointAmount;

    // Getters and Setters
}
