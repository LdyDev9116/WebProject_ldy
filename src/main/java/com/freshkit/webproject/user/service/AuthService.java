package com.freshkit.webproject.user.service;

import com.freshkit.webproject.jwt.JwtUtil;
import com.freshkit.webproject.user.dto.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthService {
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 실제 사용자 인증 로직 (예시)
     * @param memberId 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 인증된 사용자 엔티티
     */
    public UserEntity authenticate(String memberId, String password) {
        // 실제 사용자 인증 로직
        if ("testuser".equals(memberId) && "password".equals(password)) {
            return new UserEntity(memberId, password, "Test User", "testuser@example.com", "010-1234-5678", "USER"); // 예시 데이터
        }
        return null;
    }

    /**
     * 사용자가 로그인 상태인지 확인
     * @param request HTTP 요청
     * @return 로그인 상태 여부
     */
    public Boolean isLoggedIn(HttpServletRequest request) {
        String jwtToken = jwtUtil.getCookieValue(request, "jwtToken");
        log.info("JWT Token: " + jwtToken);
        return jwtToken != null && jwtUtil.validateToken(jwtToken);
    }

    /**
     * JWT에서 사용자 아이디 추출
     * @param request HTTP 요청
     * @return 사용자 아이디
     */
    public String getUserId(HttpServletRequest request) {
        String jwtToken = jwtUtil.getCookieValue(request, "jwtToken");
        log.info("JWT Token: " + jwtToken);
        if (jwtToken != null && jwtUtil.validateToken(jwtToken)) {
            return jwtUtil.getMemberId(jwtToken);
        }
        return null;
    }
}
