package com.freshkit.webproject.jwt;

import com.freshkit.webproject.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String memberId = request.getParameter("memberId");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(memberId, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String memberId = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        long jwtExpirationTime = jwtUtil.getExpirationTime();
        long refreshTokenExpirationTime = jwtUtil.getRefreshExpirationTime();

        String jwtToken = jwtUtil.createJwt(memberId, role, jwtExpirationTime);
        String refreshToken = jwtUtil.createRefreshToken(memberId, role, refreshTokenExpirationTime);

        // 콘솔에 memberId 출력
        System.out.println("Access memberId: " + memberId);
        System.out.println("Access role: " + role);

        // 콘솔에 토큰 출력
        System.out.println("successfulAuthentication 값: !!! success임 ");
        System.out.println("Access Token: " + jwtToken);
        System.out.println("Refresh Token: " + refreshToken);

        // JWT 토큰을 쿠키에 설정
        Cookie accessTokenCookie = new Cookie("accessToken", jwtToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) jwtExpirationTime / 1000); // 설정된 시간 동안 유효하도록 설정
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) refreshTokenExpirationTime / 1000); // 설정된 시간 동안 유효하도록 설정
        response.addCookie(refreshTokenCookie);

        // 세션에 memberId와 role 설정
        request.getSession().setAttribute("memberId", memberId);
        request.getSession().setAttribute("role", role);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = "{\"jwtToken\": \"" + jwtToken + "\", \"refreshToken\": \"" + refreshToken + "\", \"memberId\": \"" + memberId + "\", \"role\": \"" + role + "\"}";
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("unsuccessfulAuthentication 값: !!!  : Fail임 " + failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
