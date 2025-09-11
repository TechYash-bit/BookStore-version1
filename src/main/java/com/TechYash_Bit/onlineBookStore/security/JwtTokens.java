package com.TechYash_Bit.onlineBookStore.security;

import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokens {
    @Value("${jwt.secrateKey}")
    String secrateKey;
    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationMs;

    private SecretKey getSecrateKey(){
        return Keys.hmacShaKeyFor(secrateKey.getBytes(StandardCharsets.UTF_8));
    }

    public String genrateAccessToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getSecrateKey())
                .compact();
    }

    public String genrateRefreshToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+refreshExpirationMs))
                .signWith(getSecrateKey())
                .compact();
    }

    public String getUserNameFromeToken(String token) {
        Claims claims=Jwts.parser().verifyWith(getSecrateKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}
