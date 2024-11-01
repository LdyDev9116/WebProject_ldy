package com.freshkit.webproject.config;

import com.freshkit.webproject.jwt.JwtAuthenticationFilter;
import com.freshkit.webproject.jwt.JwtUtil;
import com.freshkit.webproject.jwt.LoginFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("assets/**", "/css/**", "/js/**", "/img/**", "/vendor/**", "/favicon/**").permitAll()  // 정적 자원 경로 허용
                        .requestMatchers(
                                "/api/products",
                                "/create-payment",
                                "/payment-success/**",
                                "/payment-success",
                                "/api/v1/payment/verify",
                                "/payment-failure",
                                "/payment-failure/**",
                                "/cart_test",
                                "/order-success",
                                "/order-success/**",
                                "/create-order",
                                "/order-summary",
                                "/order-summary/**",
                                "/main3",
                                "/check-token",
                                "/extend-token",
                                "/some-path",
                                "/remove-all-from-cart",
                                "/remove-selected-from-cart",
                                "/remove-from-cart",
                                "/update-cart",
                                "/add-to-cart",
                                "/shop-cart",
                                "/shop-single",
                                "/account-profile",
                                "/profile",
                                "/account-signup",
                                "/account-signin",
                                "/prd3",
                                "/login",
                                "/signup",
                                "/signin",
                                "/error-404",
                                "/login.html",
                                "/main.html",
                                "/user",
                                "/",
                                "/join",
                                "/index",
                                "/main",
                                "/prdtemp",
                                "/productList",
                                "/api/token/refresh",
                                "/public/**"
                        ).permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/account-signout")
                        .logoutSuccessUrl("/account-signin?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("jwtToken")
                        .permitAll());

        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/");
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                );
    }
}
