package com.freshkit.webproject.user.controller;

import com.freshkit.webproject.jwt.JwtUtil;
import com.freshkit.webproject.product.dto.OrderDto;
import com.freshkit.webproject.user.service.AuthService;
import com.freshkit.webproject.user.service.MyPageService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyPageController {

    private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);
    private final JwtUtil jwtUtil;
    private final MyPageService myPageService;
    @Autowired
    private AuthService authService;

    public MyPageController(JwtUtil jwtUtil, MyPageService myPageService) {
        this.jwtUtil = jwtUtil;
        this.myPageService = myPageService;
    }

    @GetMapping("/account-profile")
    public String accountProfilePage(HttpServletRequest request, Model model) {
        String jwtToken = getCookieValue(request, "jwtToken");
        Boolean isLoggedIn = authService.isLoggedIn(request);
        model.addAttribute("isLoggedIn", isLoggedIn != null && isLoggedIn);

        if (jwtToken != null && !jwtToken.isEmpty() && !jwtUtil.isExpired(jwtToken)) {
            Claims claims = jwtUtil.getClaims(jwtToken);
            String memberId = String.valueOf(claims.getSubject());

            List<OrderDto> orders = myPageService.getUserOrders(memberId);
            logger.info("memberId: {}", memberId);
            logger.info("Orders: {}", orders); // 데이터 확인 로그 추가

            if (orders == null || orders.isEmpty()) {
                model.addAttribute("orders", List.of()); // 빈 리스트로 초기화
                model.addAttribute("message", "주문 내역이 없습니다.");
            } else {
                model.addAttribute("orders", orders);
            }
        } else {
            model.addAttribute("message", "JWT Token is missing or invalid");
        }

        return "account-profile";
    }

    // 쿠키에서 값 가져오기 메소드
    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    logger.info("쿠키 발견 {}: {}", name, cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        logger.warn("쿠키 {}을(를) 찾을 수 없습니다", name);
        return null;
    }
}
