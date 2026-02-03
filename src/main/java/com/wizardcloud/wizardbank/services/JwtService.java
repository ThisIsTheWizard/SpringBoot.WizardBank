package com.wizardcloud.wizardbank.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wizardcloud.wizardbank.config.JwtProperties;

import com.wizardcloud.wizardbank.entities.UserEntity;

@Service
public class JwtService {
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(
            jwtProperties.getSecret().getToken().getBytes()
        );
    }

    public String generateAccessToken(UserEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("USER_IS_REQUIRED");
        }

        String accessToken = generateToken(
            user.getId().toString(),
            Map.of("type", "access", "email", user.getEmail(), "roles", user.getRoles()),
            jwtProperties.getAccessToken().getExpirationMs()
        );

        System.out.println("Generated access token for user " + user.getUsername() + ": " + accessToken);

        return accessToken;
    }

    public String generateRefreshToken(UserEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("USER_IS_REQUIRED");
        }

        String refreshToken = generateToken(
            user.getId().toString(),
            Map.of("type", "refresh", "email", user.getEmail()),
            jwtProperties.getRefreshToken().getExpiration()
        );

        System.out.println("Generated access token for user " + user.getUsername() + ": " + refreshToken);

        return refreshToken;
    }

    public String generateToken(String userId, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
            .claims(claims)
            .subject(userId)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token.replace("Bearer ", ""))
            .getPayload();
    }
}
