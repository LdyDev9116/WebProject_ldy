package com.freshkit.webproject.user.mapper;

import com.freshkit.webproject.product.dto.OrderDto;
import com.freshkit.webproject.product.dto.OrderItemDto;
import com.freshkit.webproject.product.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageMapper {

    List<OrderDto> getOrdersByUserId(@Param("memberId") String memberId);

    List<ProductDto> getProductsByOrderId(@Param("memberId") String memberId);

    List<OrderItemDto> getOrderItemsByOrderId(String orderId);
}
