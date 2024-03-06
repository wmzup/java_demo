package com.example.java_spring_boot.config;

import com.example.java_spring_boot.service.LoginServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry ->
            registry.requestMatchers(HttpMethod.GET, "/users/?*").hasAnyAuthority("ADMIN", "NORMAL")
                    .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                    .anyRequest()
                    .permitAll())
        .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public LoginServiceImpl loginServiceImpl(
            @Value("${security.jwt.key}") String key,
            @Value("${security.access-token-ttl-seconds}") int accessTokenTtlSeconds,
            @Value("${security.refresh-token-ttl-seconds}") int refreshTokenTtlSeconds,
            AuthenticationProvider authenticationProvider) {
        // key的字串要夠長，不然在製作密鑰時會出現錯誤訊息：The specified key byte array is 240 bits which is not secure enough for any JWT HMAC-SHA algorithm.
        var jwtSecretKey = Keys.hmacShaKeyFor(key.getBytes());
        var jwtParser = Jwts.parserBuilder().setSigningKey(jwtSecretKey).build();
        return new LoginServiceImpl(jwtSecretKey, accessTokenTtlSeconds, refreshTokenTtlSeconds, jwtParser, authenticationProvider);
    }
}