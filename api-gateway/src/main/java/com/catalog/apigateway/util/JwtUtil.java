package com.catalog.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public void validateToken(final String token) {
        Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
    }

    private SecretKey getSignKey() {
        // Usiamo BASE64URL o RAW per chiavi generate come stringhe esadecimali/plain
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secret);
        // Se la chiave non è Base64 valida, il sistema fallisce.
        // Per semplicità e coerenza con i test precedenti, usiamo Keys.hmacShaKeyFor con i bytes della stringa:
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractUserId(String token) {
        Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}