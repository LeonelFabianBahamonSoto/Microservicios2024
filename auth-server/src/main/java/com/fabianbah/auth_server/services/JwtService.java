package com.fabianbah.auth_server.services;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fabianbah.auth_server.exception.BusinessException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${application.jwt.secret}")
    private String JWT_SECRET;

    @Value("${application.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    private Claims getAllClaimsFromToken(String token) {
        final var key = getSecretKey();

        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final var claims = this.getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    private Date getExpirationDateToken(String token) {
        return this.getClaimsFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final var expirationDate = this.getExpirationDateToken(token);

        return expirationDate.before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return this.getClaimsFromToken(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        final Map<String, Object> claims = Collections.singletonMap("Roles", userDetails.getAuthorities().toString());

        return this.getToken(claims, userDetails.getUsername());
    }

    private String getToken(Map<String, Object> claims, String subject) {
        final var key = getSecretKey();

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String usernameFromUserDetails = userDetails.getUsername();
            final String usernameFromJwt = this.getUsernameFromToken(token);

            return (usernameFromUserDetails.equals(usernameFromJwt)) && !this.isTokenExpired(token);
        } catch (Exception e) {
            throw new BusinessException("Expired JWT", HttpStatus.UNAUTHORIZED);
        }
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
