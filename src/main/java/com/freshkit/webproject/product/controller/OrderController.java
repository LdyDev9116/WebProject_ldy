package com.freshkit.webproject.product.controller;

import com.freshkit.webproject.payment.controller.PaymentController;
import com.freshkit.webproject.payment.service.PaymentService;
import com.freshkit.webproject.product.dto.OrderDto;
import com.freshkit.webproject.product.dto.AddressDTO;
import com.freshkit.webproject.product.service.AddressService;
import com.freshkit.webproject.product.service.OrderService;
import com.freshkit.webproject.user.mapper.UserMapper;
import com.freshkit.webproject.user.service.AuthService;
import com.freshkit.webproject.user.dto.UserEntity;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserMapper userMapper; // userMapper 주입

    @PostMapping("/create-payment")
    public String createPayment(@RequestBody OrderDto orderDto, HttpSession session, Model model) {
        try {
            System.out.println("카카오 결제 요청: " + orderDto);

            String memberId = (String) session.getAttribute("memberId");
            System.out.println("memberId 의 값: " + memberId);
            logger.info("세션에서 가져온 memberId: {}", memberId);

            if (memberId != null) {
                UserEntity user = userMapper.findByUsername(memberId);
                if (user != null) {
                    model.addAttribute("username", user.getName());
                    model.addAttribute("userphone", user.getPhone());
                    model.addAttribute("useremail", user.getEmail());
                }

                // OrderDto에 memberId 설정
                orderDto.setMemberId(memberId);
            } else {
                return "redirect:/account-signin"; // 세션에 memberId가 없으면 로그인 페이지로 리디렉션
            }

            String orderId = orderService.createOrder(orderDto);
            session.setAttribute("orderDto", orderDto);
            session.setAttribute("orderId", orderId);

            logger.info("결제 검증 요청: imp_uid={}, merchant_uid={}", orderDto.getImpUid(), orderDto.getMerchantUid());

            return "redirect:/payment-success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/order-failure";
        }
    }


    @PostMapping("/create-order")
    public String createOrder(@RequestBody OrderDto orderDto, HttpSession session) {
        try {
            System.out.println("장바구니 주문 요청: " + orderDto);

            // 세션에서 memberId 가져오기
            String memberId = (String) session.getAttribute("memberId");
            System.out.println("memberId 의 값: " + memberId);

            if (memberId == null) {
                // memberId가 없으면 로그인 페이지로 리디렉션
                return "redirect:/account-signin";
            }

            // orderDto에 memberId 설정
            orderDto.setMemberId(memberId);

            // 주문 정보를 세션에 저장
            session.setAttribute("orderDto", orderDto);
            return "redirect:/order-summary";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/order-failure";
        }
    }


    @GetMapping("/order-summary")
    public String getOrderSummary(Model model, HttpServletRequest request) {
        try {
            // 로그인 상태 확인 및 모델에 추가
            Boolean isLoggedIn = authService.isLoggedIn(request);
            model.addAttribute("isLoggedIn", isLoggedIn != null && isLoggedIn);

            // 세션에서 주문 정보 가져오기
            HttpSession session = request.getSession();
            OrderDto orderDto = (OrderDto) session.getAttribute("orderDto");

            model.addAttribute("order", orderDto);

            // 로그인한 사용자의 주소 정보 조회 및 모델에 추가
            String memberId = (String) session.getAttribute("memberId");
            if (memberId == null) {
                throw new Exception("세션에 memberId가 설정되지 않았습니다.");
            }

            logger.info("세션에서 가져온 memberId: {}", memberId);

            List<AddressDTO> addresses = addressService.getAddressesByMemberId(memberId);
            model.addAttribute("addresses", addresses);

            // 사용자의 이름 조회하여 모델에 추가
            UserEntity user = userMapper.findByUsername(memberId); // 사용자 서비스에서 이름 조회
            if (user != null) {
                model.addAttribute("username", user.getName());
                model.addAttribute("userphone", user.getPhone());
                model.addAttribute("useremail", user.getEmail());
            }

            return "order-summary"; // 뷰 템플릿 이름
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("주문 정보를 불러오는 데 실패했습니다: " + e.getMessage());
        }
    }

    // 카카오 결제 API 결제하기 버튼 처리
    @PostMapping("/api/v1/payment/verify")
    @ResponseBody
    public ResponseEntity<IamportResponse<Payment>> verifyPayment(@RequestBody Map<String, String> paymentData) throws Exception {
        String impUid = paymentData.get("imp_uid");
        String merchantUid = paymentData.get("merchant_uid");

        logger.info("결제 검증 요청: imp_uid={}, merchant_uid={}", impUid, merchantUid);

        return ResponseEntity.ok(paymentService.verifyPayment(impUid, merchantUid));
    }

    // 결제 성공 페이지 GET 화면
    @GetMapping("/payment-success")
    public String paymentSuccess() {
        return "payment-success";  // 뷰 이름 반환
    }

    @GetMapping("/payment-failure")
    public String paymentFailure() {
        return "payment-failure"; // 실패 페이지 뷰 이름
    }
}
