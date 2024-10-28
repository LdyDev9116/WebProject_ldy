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

//    @GetMapping("/")
//    public String home() {
//        return "Welcome to the homepage!";
//    }
    @GetMapping("/item")
    public ItemDto getItem(@RequestParam(value = "id" ) String id) {
//    public ItemDto getItem(@RequestParam(value = "id" ,defaultValue = "111") String id) {
        ItemDto res = quickService.getItemById(id);
        log.info("GetMapping (item)서비스실행 ============================================== ");
        return res;
    }


//
//    @GetMapping("/test")
//    public  String test(){
//        log.info("로그 테스트2");
//
//        return "{test}";
//    }
//
//    @GetMapping("/member")
//    public String getMember(@RequestParam("empNo") String empNo, @RequestParam("year") int year) {
//        log.info("호출되는 로그임 : 멤버 {} " , empNo);
//        log.info("호출되는 로그임 : 멤버 {} " , year);
//        return "{ok ㅋㅋㅋㅋ}";
//    }
//
//    @GetMapping("/company/{id}")
//    public String getCompany(@PathVariable("id") String id) {
//        log.info("호출되는 로그임 : company {} " , id);
//        return "{ok ㅋㅋㅋㅋ}";
//    }
//
//    @PostMapping("/item")
//    public ResponsDto registerItem(@RequestBody ItemDto item) {
//        log.info("Item: {}", item);
//
//        boolean b = quickService.temp(item);
//        if (b == true) {
//
//            ResponsDto responsDto = new ResponsDto();
//            responsDto.setMassage("ok------------------------------------");
//            return responsDto;
//        } else {
//            ResponsDto responsDto = new ResponsDto();
//            responsDto.setMassage("fail------------------------------------");
//            return responsDto;
//        }
//
//    }
//



}