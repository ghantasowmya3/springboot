package sism.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    private Key key;
    
    @PostConstruct
    public void init() {
        // Ensure secret is at least 32 bytes for HS256
        if (secret.length() < 32) {
            secret = secret + "123456789012345678901234567890";
        }
        byte[] keyBytes = secret.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("JWT Service initialized with secret key length: " + keyBytes.length);
    }
    
    public String generateToken(Integer userId, String email, Integer role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("crid", userId);
        claims.put("email", email);
        claims.put("role", role);
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        
        System.out.println("Generated token for user: " + email);
        return token;
    }
    
    public Claims validateToken(String token) {
        try {
            System.out.println("Validating token: " + token.substring(0, Math.min(50, token.length())) + "...");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Token valid for user: " + claims.getSubject());
            return claims;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            throw e;
        }
    }
    
    public Integer getUserIdFromToken(String token) {
        Claims claims = validateToken(token);
        Object crid = claims.get("crid");
        if (crid instanceof Integer) {
            return (Integer) crid;
        } else if (crid instanceof Long) {
            return ((Long) crid).intValue();
        } else if (crid instanceof String) {
            return Integer.parseInt((String) crid);
        }
        return (Integer) claims.get("crid");
    }
    
    public String getEmailFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }
}