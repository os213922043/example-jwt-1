package com.example.examplejson.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component

public class JwtProvider {

    private static final String SECRET_KEY = "verystrongsecretkeyverystrongsecretkey1234"; // Kamida 32 bayt
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000*24)) // 1 kun
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        String subject = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        return subject;
    }
}
