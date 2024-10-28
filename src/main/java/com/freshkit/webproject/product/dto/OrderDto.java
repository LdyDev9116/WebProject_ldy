package com.freshkit.webproject.product.dto;


import com.freshkit.webproject.user.dto.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private String orderId;
    private Integer totalPrice;
    private Date createdAt;
    private String impUid;
    private String merchantUid;
    private String productName;
    private String phone;
    private String email;
    private String postcode;
    private String address;
    private String detailAddress;
    private String recipientName;
    private String phoneNumber;
    private Integer amount;
    private Integer pointAmount;
    private Date deliveryDate; // 배송희망일
    private String deliveryRequest; // 배송요청사항
    private String memberId; // memberId 필드 추가
    private String name; // name 필드 추가
    private List<OrderItemDto> orderItems;
    private UserEntity user; // UserEntity 필드 추가

}