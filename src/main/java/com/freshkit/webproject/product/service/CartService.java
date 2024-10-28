package com.freshkit.webproject.product.service;

import com.freshkit.webproject.product.dto.OrderItemDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private final Map<String, List<OrderItemDto>> userCarts = new HashMap<>();

    public void addToCart(String userId, Long productId, String productName, int quantity, int price) {
        List<OrderItemDto> orderItems = userCarts.getOrDefault(userId, new ArrayList<>());
        boolean itemExists = false;

        // Check if item already exists in the cart
        for (OrderItemDto item : orderItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setTotalPrice(item.getQuantity() * item.getPrice());
                itemExists = true;
                break;
            }
        }

        // If item does not exist, add new item to the cart
        if (!itemExists) {
            orderItems.add(new OrderItemDto(productId, productName, quantity, (int) price));
        }

        userCarts.put(userId, orderItems);
    }

    public List<OrderItemDto> getorderItems(String userId) {
        return userCarts.getOrDefault(userId, new ArrayList<>());
    }

    public void clearCart(String userId) {
        userCarts.remove(userId);
    }

    public void removeFromCart(String userId, Long productId) {
        List<OrderItemDto> orderItems = userCarts.get(userId);
        if (orderItems != null) {
            orderItems.removeIf(item -> item.getProductId().equals(productId));
            userCarts.put(userId, orderItems);
        }
    }

    // 수량 감소
    public void decrementCartItem(String userId, Long productId) {
        List<OrderItemDto> orderItems = userCarts.get(userId);
        if (orderItems != null) {
            for (OrderItemDto item : orderItems) {
                if (item.getProductId().equals(productId) && item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    item.setTotalPrice(item.getQuantity() * item.getPrice());
                    break;
                }
            }
        }
    }

    // 수량 증가
    public void incrementCartItem(String userId, Long productId) {
        List<OrderItemDto> orderItems = userCarts.get(userId);
        if (orderItems != null) {
            for (OrderItemDto item : orderItems) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + 1);
                    item.setTotalPrice(item.getQuantity() * item.getPrice());
                    break;
                }
            }
        }
    }
    //새로고침 이후에도 삭제 유지
    public void removeSelectedFromCart(String userId, List<Long> productIds) {
        List<OrderItemDto> orderItems = userCarts.get(userId);
        if (orderItems != null) {
            orderItems.removeIf(item -> productIds.contains(item.getProductId()));
            userCarts.put(userId, orderItems);
        }
    }



}
