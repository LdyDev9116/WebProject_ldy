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
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    // 로깅을 위한 Logger 설정
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    // 서비스와 매퍼 객체를 자동 주입
    @Autowired
    private AuthService authService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 카카오 결제를 생성하는 메서드입니다.
     * @param orderDto 주문 정보가 담긴 데이터 전송 객체
     * @param session 현재 HTTP 세션으로 세션에 회원 정보를 저장하는 데 사용
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 결제 성공 시 성공 페이지로 리다이렉트, 실패 시 실패 페이지로 리다이렉트
     */
    @PostMapping("/create-payment")
    public String createPayment(@RequestBody OrderDto orderDto, HttpSession session, Model model) {
        try {
            System.out.println("카카오 결제 요청: " + orderDto);

            // 세션에서 회원 ID 가져오기
            String memberId = (String) session.getAttribute("memberId");
            logger.info("세션에서 가져온 memberId: {}", memberId);

            if (memberId != null) {
                // 회원 정보 조회 후 모델에 사용자 정보 추가
                UserEntity user = userMapper.findByUsername(memberId);
                if (user != null) {
                    model.addAttribute("username", user.getName());
                    model.addAttribute("userphone", user.getPhone());
                    model.addAttribute("useremail", user.getEmail());
                }

                // OrderDto에 회원 ID 설정
                orderDto.setMemberId(memberId);
            } else {
                // 로그인하지 않은 경우 로그인 페이지로 리디렉션
                return "redirect:/account-signin";
            }

            // 주문 생성 및 세션에 저장
            String orderId = orderService.createOrder(orderDto);
            session.setAttribute("orderDto", orderDto);
            session.setAttribute("orderId", orderId);

            // 결제 검증 요청 로그
            logger.info("결제 검증 요청: imp_uid={}, merchant_uid={}", orderDto.getImpUid(), orderDto.getMerchantUid());

            // 결제 성공 페이지로 리디렉션
            return "redirect:/payment-success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/order-failure";
        }
    }

    /**
     * 장바구니에서 주문을 생성하는 메서드입니다.
     * @param orderDto 주문 정보가 담긴 데이터 전송 객체
     * @param session 현재 HTTP 세션으로 세션에 주문 정보를 저장하는 데 사용
     * @return 주문 요약 페이지로 리디렉션하거나, 실패 시 실패 페이지로 리디렉션
     */
    @PostMapping("/create-order")
    public String createOrder(@RequestBody OrderDto orderDto, HttpSession session) {
        try {
            System.out.println("장바구니 주문 요청: " + orderDto);

            // 세션에서 회원 ID 가져오기
            String memberId = (String) session.getAttribute("memberId");

            if (memberId == null) {
                // 로그인하지 않은 경우 로그인 페이지로 리디렉션
                return "redirect:/account-signin";
            }

            // OrderDto에 회원 ID 설정
            orderDto.setMemberId(memberId);

            // 주문 정보를 세션에 저장
            session.setAttribute("orderDto", orderDto);
            return "redirect:/order-summary";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/order-failure";
        }
    }

    /**
     * 주문 요약 정보를 가져오는 메서드입니다.
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @param request 현재 HTTP 요청 객체로 로그인 상태를 확인하는 데 사용
     * @return 주문 요약 페이지 뷰 이름
     */
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

            // 사용자 주소 정보 조회
            List<AddressDTO> addresses = addressService.getAddressesByMemberId(memberId);
            model.addAttribute("addresses", addresses);

            // 사용자 정보 조회 및 모델에 추가
            UserEntity user = userMapper.findByUsername(memberId);
            if (user != null) {
                model.addAttribute("username", user.getName());
                model.addAttribute("userphone", user.getPhone());
                model.addAttribute("useremail", user.getEmail());
            }

            return "order-summary";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("주문 정보를 불러오는 데 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 결제 검증 요청을 처리하는 API 엔드포인트입니다. (카카오 결제 API 사용)
     * @param paymentData 결제 정보가 담긴 요청 데이터
     * @return 결제 검증 결과를 포함한 IamportResponse 객체
     * @throws Exception 결제 검증 중 예외 발생 시
     */
    @PostMapping("/api/v1/payment/verify")
    @ResponseBody
    public ResponseEntity<IamportResponse<Payment>> verifyPayment(@RequestBody Map<String, String> paymentData) throws Exception {
        String impUid = paymentData.get("imp_uid");
        String merchantUid = paymentData.get("merchant_uid");

        logger.info("결제 검증 요청: imp_uid={}, merchant_uid={}", impUid, merchantUid);

        // 결제 검증 결과 반환
        return ResponseEntity.ok(paymentService.verifyPayment(impUid, merchantUid));
    }

    /**
     * 결제 성공 페이지로 이동합니다.
     * @return 결제 성공 페이지 뷰 이름
     */
    @GetMapping("/payment-success")
    public String paymentSuccess() {
        return "payment-success";
    }

    /**
     * 결제 실패 페이지로 이동합니다.
     * @return 결제 실패 페이지 뷰 이름
     */
    @GetMapping("/payment-failure")
    public String paymentFailure() {
        return "payment-failure";
    }
}
