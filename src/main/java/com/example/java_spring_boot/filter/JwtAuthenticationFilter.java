package com.example.java_spring_boot.filter;

import com.example.java_spring_boot.service.LoginService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;


@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final LoginService loginService;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(LoginService loginService, UserDetailsService userDetailsService) {
        this.loginService = loginService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 取得token的值
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null) {
            String accessToken = bearerToken.replace("Bearer ", "");

            // 解析token
            Map<String, Object> tokenPayload = loginService.parseToken(accessToken);
            String username = (String) tokenPayload.get("username");

            // 查詢使用者
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 將使用者身分與權限傳遞給Spring Security
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 將request送往controller或下一個filter
        filterChain.doFilter(request, response);
    }
}
