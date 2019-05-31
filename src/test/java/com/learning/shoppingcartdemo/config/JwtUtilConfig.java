package com.learning.shoppingcartdemo.config;

import com.learning.shoppingcartdemo.service.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.learning.shoppingcartdemo.model.Role.ROLE_USER;

@TestConfiguration
public class JwtUtilConfig {
    @Bean
    @Primary
    public MockJwtUtil mockJwtUtil() {
        return new MockJwtUtil();
    }

    private class MockJwtUtil extends JWTUtil {
        MockJwtUtil() {
            super(1L);
        }

        @Override
        public Claims getAllClaimsFromToken(String token) {
            HashMap<String, Object> authorities = new HashMap<>(Map.of("authorities", List.of(ROLE_USER.name())));
            return new DefaultClaims(authorities);
        }

        @Override
        public String getUsernameFromToken(String token) {
            return "test-user";
        }

        @Override
        public Boolean validateToken(String token) {
            return true;
        }
    }
}
