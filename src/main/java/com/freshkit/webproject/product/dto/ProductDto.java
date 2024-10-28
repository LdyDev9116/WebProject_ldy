package com.freshkit.webproject.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
        private Long productId;
        private String name;
        private String storeName;
        private String category;
        private String brand;
        private int price;
        private String thumbnailUrl;
        private String contentUrl;
        private boolean isSoldOut;
        private String detail;
        private double rating;
        private int popularity;
        private String dateAdded;
        private String description;
        @Override
        public String toString() {
                return "ProductDto{" +
                        "productId=" + productId +
                        ", name='" + name + '\'' +
                        ", storeName='" + storeName + '\'' +
                        ", category='" + category + '\'' +
                        ", brand='" + brand + '\'' +
                        ", price=" + price +
                        ", thumbnailUrl='" + thumbnailUrl + '\'' +
                        ", contentUrl='" + contentUrl + '\'' +
                        ", isSoldOut=" + isSoldOut +
                        ", detail='" + detail + '\'' +
                        ", rating=" + rating +
                        ", popularity=" + popularity +
                        ", dateAdded='" + dateAdded + '\'' +
                        '}';
        }
}
