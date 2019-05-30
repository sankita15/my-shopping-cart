package com.learning.shoppingcartdemo.config;

import com.learning.shoppingcartdemo.service.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username = getUsername(authToken);
        if(username != null && jwtUtil.validateToken(authToken)){
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);

            var auth = new UsernamePasswordAuthenticationToken(
                username, null, getAuthorities(claims.get("authorities", List.class))
            );
            return Mono.just(auth);
        }
        return Mono.empty();

    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    private String getUsername(String authToken) {
        return jwtUtil.getUsernameFromToken(authToken);
    }
}
