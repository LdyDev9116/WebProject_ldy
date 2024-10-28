package com.freshkit.webproject.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCartResponse {
    private boolean success;
    private String message;
    private int newQuantity;
}
