package com.commercehub.auth_service.security;

import com.commercehub.auth_service.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private long expiration;

    private final JwtProperties jwtProperties;


    public String generateToken(UserDetails userDetails) {
        System.out.println("Generating token with secret: " + jwtProperties.getSecret());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities()
                        .stream()
                        .findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .orElse("ROLE_USER"))
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis()
                                + jwtProperties.getExpiration()))
                .signWith(getSigningKey())
                .compact();
    }


    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {


        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );

    }

    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );

    }

    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());

    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);

    }

    private Claims extractAllClaims(String token) {

        System.out.println("Validating token with secret: " + jwtProperties.getSecret());

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostConstruct
    public void printSecret() {
        System.out.println("JWT Secret = " + jwtProperties.getSecret());
    }
}
