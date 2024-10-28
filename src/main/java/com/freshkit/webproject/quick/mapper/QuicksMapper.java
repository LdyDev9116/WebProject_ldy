package com.freshkit.webproject.quick.mapper;

//import com.ssg.wsmt.smOrders.domain.SmOrdersVo;
//
//import com.ssg.wsmt.smOrders.domain.SmOrdersVo;
//import com.ssg.wsmt.smOrders.dto.SmOrdersDTO;
//import com.ssg.wsmt.smOrders.enums.SellerSendStatus;

import com.freshkit.webproject.quick.dto.ItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface QuicksMapper {
    void registerItem(HashMap<String, Object> paramMap);

    //HashMap로 SQL 받아올때
    //HashMap<String, Object> findById(HashMap<String, Object> pram);

    // ItemDto로 SQL 받아올때
    ItemDto findById(HashMap<String, Object> pram);

//    void insertUser(UserDTO userdto);
}