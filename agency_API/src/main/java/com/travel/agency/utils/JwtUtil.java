package com.travel.agency.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY;

    public JwtUtil() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            this.SECRET_KEY = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder().claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuer("Explora+")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .and()
                .signWith(getKey())
                .compact();

}

    private SecretKey getKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }

}
