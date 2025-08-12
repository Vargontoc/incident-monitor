package es.agonzalez.incident.monitor.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    
    @Value("${app.jwt.secret}") String secret;
    @Value("${app.jwt.access-minutes}") long accessMillis;
    @Value("${app.jwt.refresh-days}") long refreshMillis;

    private  Key key;
    @PostConstruct
    public void init() {
        // Constructor logic if needed
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessMillis = accessMillis * 60 * 1000; // Convert minutes to milliseconds
        this.refreshMillis = refreshMillis * 24 * 60 * 60 * 1000; // Convert days to milliseconds   
    }

    public String generateAccessToken(String username) {
        return buildToke(username, accessMillis);
    }

    public String generateRefreshToken(String username) {
        return buildToke(username, refreshMillis);
    }

    private String buildToke(String sub,long ttl) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(sub)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusMillis(ttl)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }
}
