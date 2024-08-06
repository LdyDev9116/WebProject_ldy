package com.freshkit.webproject.payment.controller;

import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
//    private final PaymentService paymentService;
//
//    public PaymentController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }
//
//    @GetMapping("/cart_test")
//    public String showCart() {
//        return "cart_test";
//    }
//
//    @PostMapping("/api/v1/payment/verify")
//    @ResponseBody
//    public ResponseEntity<IamportResponse<Payment>> verifyPayment(@RequestBody Map<String, String> paymentData) throws Exception {
//        String impUid = paymentData.get("imp_uid");
//        String merchantUid = paymentData.get("merchant_uid");
//
//        logger.info("결제 검증 요청: imp_uid={}, merchant_uid={}", impUid, merchantUid);
//
//        return ResponseEntity.ok(paymentService.verifyPayment(impUid, merchantUid));
//    }
//
//    @PostMapping("/api/order")
//    @ResponseBody
//    public ResponseEntity<?> processOrder(@Validated @RequestBody PaymentDto paymentDto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
//        }
//        paymentService.processOrder(paymentDto);
//        return ResponseEntity.ok("success");
//    }
//
//    @GetMapping("/payment-success")
//    public String paymentSuccess(@RequestParam("impUid") String impUid,
//                                 @RequestParam("merchantUid") String merchantUid,
//                                 Model model) {
//        try {
//            paymentService.verifyPayment(impUid, merchantUid);
//            model.addAttribute("impUid", impUid);
//            model.addAttribute("merchantUid", merchantUid);
//            return "payment-success"; // 성공 페이지 뷰 이름
//        } catch (Exception e) {
//            return "redirect:/payment-failure"; // 실패 페이지로 리다이렉트
//        }
//    }
//
//    @GetMapping("/payment-failure")
//    public String paymentFailure() {
//        return "payment-failure"; // 실패 페이지 뷰 이름
//    }
}
