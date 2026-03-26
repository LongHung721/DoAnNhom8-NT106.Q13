/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;
/**
 *
 * @author ASUS
 */
@Component
public class JWTUtil {
     private static final String SECRET_KEY = 
            "gjzgBnMadf6yBxfWa5GR0N1+ao42OGdgiTVMVkDat6U=";

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000 * 60 * 60)) 
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Jws<Claims> validateToken(String token) {
         return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token);
    }
}
