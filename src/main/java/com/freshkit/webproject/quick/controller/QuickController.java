package com.freshkit.webproject.quick.controller;

import com.freshkit.webproject.quick.dto.ItemDto;
import com.freshkit.webproject.quick.service.QuickService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class QuickController {

    @Autowired
    private QuickService quickService;

    @GetMapping("/item")
    public ItemDto getItem(@RequestParam(value = "id" ) String id) {
//    public ItemDto getItem(@RequestParam(value = "id" ,defaultValue = "111") String id) {
        ItemDto res = quickService.getItemById(id);
        log.info("GetMapping (item)서비스실행 ============================================== ");
        return res;
    }


}