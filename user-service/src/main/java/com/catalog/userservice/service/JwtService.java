package com.catalog.userservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        // Usiamo BASE64URL o RAW per chiavi generate come stringhe esadecimali/plain
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secret);
        // Se la chiave non è Base64 valida, il sistema fallisce.
        // Per semplicità e coerenza con i test precedenti, usiamo Keys.hmacShaKeyFor con i bytes della stringa:
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}