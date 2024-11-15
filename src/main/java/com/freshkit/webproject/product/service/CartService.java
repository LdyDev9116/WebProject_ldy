package com.freshkit.webproject.product.service;

import com.freshkit.webproject.product.dto.OrderItemDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CartService는 장바구니와 관련된 기능을 제공하는 서비스 클래스입니다.
 * - 사용자별 장바구니 데이터를 관리합니다.
 * - 장바구니에 상품 추가, 삭제, 수량 변경 등의 기능을 제공합니다.
 */
@Service
public class CartService {

    // 사용자별 장바구니 데이터를 저장하는 맵 (userId -> List<OrderItemDto>)
    private final Map<String, List<OrderItemDto>> userCarts = new HashMap<>();

    /**
     * 장바구니에 제품을 추가합니다.
     * - 동일한 제품이 이미 장바구니에 있는 경우, 수량을 증가시킵니다.
     * - 동일한 제품이 없는 경우, 새로운 항목으로 추가합니다.
     *
     * @param userId 사용자 ID
     * @param productId 추가할 제품 ID
     * @param productName 제품 이름
     * @param quantity 추가할 수량
     * @param price 제품 가격
     */
    public void addToCart(String userId, Long productId, String productName, int quantity, int price) {
        List<OrderItemDto> orderItems = userCarts.getOrDefault(userId, new ArrayList<>());
        boolean itemExists = false;

        // 기존 장바구니에 동일한 제품이 있는지 확인
        for (OrderItemDto item : orderItems) {
            if (item.getProductId().equals(productId)) {
                // 동일 제품이 있으면 수량과 총 가격 업데이트
                item.setQuantity(item.getQuantity() + quantity);
                item.setTotalPrice(item.getQuantity() * item.getPrice());
                itemExists = true;
                break;
            }
        }

        // 동일 제품이 없으면 새로운 항목 추가
        if (!itemExists) {
            orderItems.add(new OrderItemDto(productId, productName, quantity, price));
        }

        userCarts.put(userId, orderItems);
    }

    /**
     * 사용자의 장바구니 항목을 조회합니다.
     * @param userId 사용자 ID
     * @return List<OrderItemDto> 형태의 장바구니 항목 목록 (항목이 없으면 빈 리스트 반환)
     */
    public List<OrderItemDto> getorderItems(String userId) {
        return userCarts.getOrDefault(userId, new ArrayList<>());
    }

    /**
     * 사용자의 장바구니를 비웁니다.
     * @param userId 사용자 ID
     */
    public void clearCart(String userId) {
        userCarts.remove(userId);
    }

    /**
     * 장바구니에서 특정 제품을 제거합니다.
     * @param userId 사용자 ID
     * @param productId 제거할 제품 ID
     */
    public void removeFromCart(String userId, Long productId) {
        List<OrderItemDto> orderItems = userCarts.get(userId);
        if (orderItems != null) {
            // 해당 제품 ID와 일치하는 항목 제거
            orderItems.removeIf(item -> item.getProductId().equals(productId));
            userCarts.put(userId, orderItems);
        }
    }

    /**
     * 장바구니에서 특정 제품의 수량을 감소시킵니다.
     * - 수량이 1 이하로 감소할 경우 아무 작업도 수행하지 않습니다.
     *
     * @param userId 사용자 ID
     * @param productId 수량을 감소시킬 제품 ID
     */
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

    /**
     * 장바구니에서 특정 제품의 수량을 증가시킵니다.
     * @param userId 사용자 ID
     * @param productId 수량을 증가시킬 제품 ID
     */
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

    /**
     * 장바구니에서 선택된 제품들을 제거합니다.
     * @param userId 사용자 ID
     * @param productIds 제거할 제품 ID 리스트
     */
    public void removeSelectedFromCart(String userId, List<Long> productIds) {
        List<OrderItemDto> orderItems = userCarts.get(userId);
        if (orderItems != null) {
            // 선택된 제품 ID와 일치하는 항목 제거
            orderItems.removeIf(item -> productIds.contains(item.getProductId()));
            userCarts.put(userId, orderItems);
        }
    }
}
