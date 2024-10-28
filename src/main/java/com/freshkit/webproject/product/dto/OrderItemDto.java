package com.freshkit.webproject.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
        private Long itemId;
        private String orderId; // 이 필드를 추가
        private Long productId;
        private String productName;
        private Integer quantity;
        private Integer price;
        private Integer totalPrice;

        public OrderItemDto(Long productId, String productName, int quantity, int price) {
                this.productId = productId;
                this.productName = productName;
                this.quantity = quantity;
                this.price = price;
                this.totalPrice = quantity * price;
        }
}
