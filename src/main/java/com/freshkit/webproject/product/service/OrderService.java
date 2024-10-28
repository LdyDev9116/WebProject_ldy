package com.freshkit.webproject.product.service;

import com.freshkit.webproject.product.dto.OrderDto;
import com.freshkit.webproject.product.dto.OrderItemDto;
import com.freshkit.webproject.product.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public String createOrder(OrderDto orderDto) {
        // 현재 날짜 설정
        orderDto.setCreatedAt(new Date());

        // 현재 날짜를 사용한 주문 번호 생성
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String orderId = "order_" + date + "_" + UUID.randomUUID().toString().substring(0, 8);

        orderDto.setOrderId(orderId); // 주문번호 세팅

        // 로그 추가: OrderDto 정보를 출력
        System.out.println("주문 정보 (OrderDto): " + orderDto);

        // 주문 정보 삽입
        orderMapper.insertOrder(orderDto);

        // 주문 아이템 정보가 비어 있는지 확인하는 로그 추가
        if (orderDto.getOrderItems() == null || orderDto.getOrderItems().isEmpty()) {
            System.out.println("주문 아이템이 비어 있습니다. (OrderItems is null or empty)");
        } else {
            System.out.println("주문 아이템 수: " + orderDto.getOrderItems().size());
        }

        // 주문 아이템 삽입
        for (OrderItemDto item : orderDto.getOrderItems()) {
            System.out.println("주문 아이템 (OrderItem) 설정 전: " + item);
            item.setOrderId(orderDto.getOrderId());
            orderMapper.insertOrderItem(item);
            System.out.println("삽입된 주문 아이템 (Inserted OrderItem): " + item);
        }

        return orderDto.getOrderId();
    }
}
