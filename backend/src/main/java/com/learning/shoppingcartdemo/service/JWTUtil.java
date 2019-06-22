package com.learning.shoppingcartdemo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class JWTUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    private final SecretKey secretKey;
    private final Long expirationTime;

    @Autowired
    public JWTUtil(@Value("${app.security.jwt.expiration}") Long expirationTime) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.expirationTime = expirationTime;
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        var claims = new HashMap<String, Object>(Map.of("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList())));
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(HashMap<String, Object> claims, String username) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(Instant.now().plusSeconds(expirationTime).toEpochMilli()))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }

}
