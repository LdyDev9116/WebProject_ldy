package com.freshkit.webproject.user.service;

import com.freshkit.webproject.product.dto.OrderDto;
import com.freshkit.webproject.user.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    private final MyPageMapper myPageMapper;

    @Autowired
    public MyPageService(MyPageMapper myPageMapper) {
        this.myPageMapper = myPageMapper;
    }

    public List<OrderDto> getUserOrders(String memberId) {
        List<OrderDto> orders = myPageMapper.getOrdersByUserId(memberId);
        for (OrderDto order : orders) {
            order.setOrderItems(myPageMapper.getOrderItemsByOrderId(order.getOrderId()));
        }
        return orders;
    }
}
