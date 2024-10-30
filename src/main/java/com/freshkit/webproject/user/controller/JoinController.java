package com.freshkit.webproject.user.controller;

import com.freshkit.webproject.user.dto.JoinEntity;
import com.freshkit.webproject.user.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JoinController {

    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

//     회원가입 페이지로 이동합니다.
    @GetMapping("/account-signup")
    public String loginPage2(Model model) {
        model.addAttribute("joinEntity", new JoinEntity());
        return "account-signup";  // Thymeleaf 템플릿 이름을 반환
    }

//     회원가입 처리를 합니다.
    @PostMapping("/account-signup")
    public ResponseEntity<String> join2(@ModelAttribute JoinEntity joinEntity) {
        try {
            joinService.joinProcess(joinEntity);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // 아이디 존재 여부를 확인합니다.
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkMemberIdExists(@RequestParam String memberId) {
        boolean exists = joinService.checkMemberIdExists(memberId);
        return ResponseEntity.ok(exists);
    }
}
