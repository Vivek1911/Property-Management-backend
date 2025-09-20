package com.example.Property.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.example.Property.model.User;

@Service
public class JWTService {

    public final String SEC_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890MKDJSHIEUFBEICBWIEOWDWUU";
    public final SecretKey key = Keys.hmacShaKeyFor(SEC_KEY.getBytes());

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
    
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(key)
                .compact();
    }
    

    public String validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return "401"; // Unauthorized
            }

            token = token.trim(); // Trim spaces before parsing

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            if (expiration == null || expiration.before(new Date())) {
                return "401";
            }

            return claims.get("email", String.class);
        } catch (Exception e) {
            return "401";
        }
    }

    
}
