package com.freshkit.webproject.quick.service;

import com.freshkit.webproject.quick.dto.ItemDto;
import com.freshkit.webproject.quick.mapper.QuicksMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@Log4j2
public class QuickService {

    @Autowired
    QuicksMapper quicksMapper;


    public boolean temp(ItemDto itemdto){
        log.info("QuickService t서비스임 ");
        return true;
    }
    public ItemDto getItemById(String id) {
        HashMap<String, Object> pram = new HashMap<>();
        pram.put("id", id);

//      HashMap<String, Object> res = smOrdersMapper.findById(pram);
        ItemDto res = quicksMapper.findById(pram);

        res.setId((String) res.getId("ID"));
        res.setName((String) res.getName("NAME"));
        log.info("getItemById 서비스실행 ============================================== ");
        return res;
    }

    public boolean registerItem(ItemDto itemDto) {
        // TODO: DB insert
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", itemDto.getId());
        paramMap.put("name", itemDto.getName());

        quicksMapper.registerItem(paramMap);
        log.info("service ...");

        return true; // 또는 데이터베이스 작업의 성공/실패에 따라 true/false 반환
    }


}
