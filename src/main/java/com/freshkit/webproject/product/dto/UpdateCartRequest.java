package com.freshkit.webproject.product.dto;

import lombok.Data;

@Data
public class UpdateCartRequest {
    private Long productId;  // cartId -> productId로 변경
    private String action;
}
