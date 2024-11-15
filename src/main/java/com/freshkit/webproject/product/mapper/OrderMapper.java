package com.freshkit.webproject.product.mapper;

import com.freshkit.webproject.product.dto.OrderDto;
import com.freshkit.webproject.product.dto.OrderItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 새로운 주문 데이터를 데이터베이스에 삽입합니다.
     * @param orderDto 삽입할 주문 데이터를 담고 있는 객체
     */
    void insertOrder(OrderDto orderDto);

    /**
     * 새로운 주문 항목 데이터를 데이터베이스에 삽입합니다.
     * @param orderItem 삽입할 주문 항목 데이터를 담고 있는 객체
     */
    void insertOrderItem(OrderItemDto orderItem);

    /**
     * 주문 ID를 기준으로 특정 주문 데이터를 조회합니다.
     * @param orderId 조회할 주문의 ID
     * @return OrderDto 형태의 주문 데이터
     */
    OrderDto selectOrderById(Long orderId);

    /**
     * 주문 ID를 기준으로 해당 주문의 모든 주문 항목을 조회합니다.
     * @param orderId 조회할 주문의 ID
     * @return List<OrderItemDto> 형태의 주문 항목 리스트
     */
    List<OrderItemDto> selectOrderItemsByOrderId(Long orderId);
}
