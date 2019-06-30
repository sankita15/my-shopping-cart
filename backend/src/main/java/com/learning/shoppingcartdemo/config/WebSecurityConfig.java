package com.learning.shoppingcartdemo.config;

import com.learning.shoppingcartdemo.repository.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http, AuthenticationManager authenticationManager,
        SecurityContextRepository securityContextRepository) {
        return http
//            .exceptionHandling()
//            .authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/login"))
//            .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
//            .and()
//            .authenticationManager(authenticationManager)
//            .securityContextRepository(securityContextRepository)
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
//            .pathMatchers("/login").permitAll()
            .anyExchange().permitAll()
            .and().build();
    }
}
