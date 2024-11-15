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

    /**
     * 주문을 생성하고, 데이터베이스에 저장하는 메서드입니다.
     * - 주문 번호를 생성하고, 주문 데이터와 주문 항목 데이터를 데이터베이스에 저장합니다.
     * - 주문 데이터와 주문 항목 데이터는 트랜잭션 내에서 함께 처리됩니다.
     *
     * @param orderDto 주문 정보를 포함한 데이터 전송 객체
     * @return 생성된 주문 ID (문자열 형태)
     */
    @Transactional
    public String createOrder(OrderDto orderDto) {
        // 현재 날짜 설정
        orderDto.setCreatedAt(new Date());

        // 주문 번호 생성: 날짜와 UUID를 조합하여 고유한 주문 번호 생성
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String orderId = "order_" + date + "_" + UUID.randomUUID().toString().substring(0, 8);

        // 주문 번호 설정
        orderDto.setOrderId(orderId);

        // 로그: 생성된 주문 정보 출력
        System.out.println("주문 정보 (OrderDto): " + orderDto);

        // 주문 정보를 데이터베이스에 삽입
        orderMapper.insertOrder(orderDto);

        // 주문 아이템 데이터가 비어 있는지 확인
        if (orderDto.getOrderItems() == null || orderDto.getOrderItems().isEmpty()) {
            // 로그: 주문 항목이 비어 있다는 경고 메시지
            System.out.println("주문 아이템이 비어 있습니다. (OrderItems is null or empty)");
        } else {
            // 로그: 주문 아이템 수 출력
            System.out.println("주문 아이템 수: " + orderDto.getOrderItems().size());
        }

        // 각 주문 항목 데이터에 주문 ID 설정 후 데이터베이스에 삽입
        for (OrderItemDto item : orderDto.getOrderItems()) {
            System.out.println("주문 아이템 (OrderItem) 설정 전: " + item);
            item.setOrderId(orderDto.getOrderId()); // 주문 아이템에 주문 ID 설정
            orderMapper.insertOrderItem(item); // 주문 아이템 삽입
            System.out.println("삽입된 주문 아이템 (Inserted OrderItem): " + item);
        }

        // 생성된 주문 ID 반환
        return orderDto.getOrderId();
    }
}
