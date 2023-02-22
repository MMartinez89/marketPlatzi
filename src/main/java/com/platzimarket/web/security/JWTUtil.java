package com.platzimarket.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Component
public class JWTUtil {

    private static final String KEY = "1234";

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

   /* public String generatedToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date( System.currentTimeMillis() + 1000 * 60 * 60*10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    };*/

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return userDetails.getUsername().equals(extracUserName(token)) && !isTokenExpired(token);
    }

    public String extracUserName(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token){
       //return  Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
       //return (Claims) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
       return (Claims) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();


    }



}
