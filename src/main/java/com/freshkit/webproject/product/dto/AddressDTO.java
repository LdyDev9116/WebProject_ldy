package com.freshkit.webproject.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private int addressId;
    private String memberId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String deliveryType;
    private boolean isDefault;

    // Getters and Setters
}
