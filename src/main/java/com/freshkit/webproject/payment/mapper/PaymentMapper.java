package com.freshkit.webproject.payment.mapper;

import com.freshkit.webproject.payment.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    void insertOrder(PaymentDto paymentDto);
}
