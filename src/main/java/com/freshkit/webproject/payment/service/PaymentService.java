package com.freshkit.webproject.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshkit.webproject.payment.dto.PaymentDto;
import com.freshkit.webproject.payment.mapper.PaymentMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private IamportClient iamportClient;
    @Value("${portone.api-key}")
    private String apiKey;
    @Value("${portone.api-secret}")
    private String apiSecret;

    private final PaymentMapper paymentMapper;
    private final RestTemplate restTemplate;

    public PaymentService(PaymentMapper paymentMapper, RestTemplate restTemplate) {
        this.paymentMapper = paymentMapper;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    public IamportResponse<Payment> verifyPayment(String impUid, String merchantUid) throws Exception {
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map responseBody = response.getBody();

            // Check if response body contains the necessary payment details
            if (responseBody != null && responseBody.containsKey("response")) {
                Map responseData = (Map) responseBody.get("response");
                if (responseData.containsKey("amount")) {
                    // Assuming "amount" is in responseData and type-casting to required type.

                    return new IamportResponse<>();
                }
            }
            throw new RuntimeException("Invalid payment verification response");

        } catch (Exception e) {
            logger.error("결제 검증 중 예외 발생: imp_uid={}, merchant_uid={}", impUid, merchantUid, e);
            throw new RuntimeException("결제 검증 중 오류 발생", e);
        }
    }

    private String getAccessToken() throws Exception {
        String url = "https://api.iamport.kr/users/getToken";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("imp_key", apiKey);
        body.put("imp_secret", apiSecret);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // 요청 본문을 JSON 문자열로 변환하여 출력
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(body);

        // 디버깅: 요청 URL과 엔터티 출력
        System.out.println("요청 URL: " + url);
        System.out.println("요청 본문: " + jsonBody);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        // 응답 디버깅 정보 추가
        System.out.println("응답 상태 코드: " + response.getStatusCode());
        System.out.println("응답 본문: " + response.getBody());

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody().get("response");
            if (responseBody != null && responseBody.containsKey("access_token")) {
                return (String) responseBody.get("access_token");
            } else {
                logger.error("응답 본문에 'access_token'이 포함되지 않았습니다");
            }
        } else {
            logger.error("PortOne API에서 액세스 토큰을 가져오는 데 실패했습니다, 상태 코드: {}", response.getStatusCode());
        }

        throw new Exception("액세스 토큰을 가져오는 데 실패했습니다");
    }

    @Transactional
    public void processOrder(PaymentDto paymentDto) {
        paymentMapper.insertOrder(paymentDto);
    }
}