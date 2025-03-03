package com.tks.chatapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.validity}")
    private Long tokenValidity;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.secretRF}")
    private String secretRF;

    public String getUsernameFormToken(String token){
        return getClaimFromToken(token, Claims :: getSubject);
    }

    public <T> T  getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
