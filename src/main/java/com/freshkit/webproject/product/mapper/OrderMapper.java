package com.freshkit.webproject.product.mapper;

import com.freshkit.webproject.product.dto.OrderDto;
import com.freshkit.webproject.product.dto.OrderItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderDto orderDto);
    void insertOrderItem(OrderItemDto orderItem);

    OrderDto selectOrderById(Long orderId);
    List<OrderItemDto> selectOrderItemsByOrderId(Long orderId);
}
