package com.banking.core.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

/**
 * JWT token provider for authentication and authorization.
 * Handles token generation, validation, and claim extraction.
 */
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final SecretKey secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private final String issuer;

    public JwtTokenProvider(
            @Value("${banking.security.jwt.secret:default-secret-key-must-be-at-least-256-bits-long-for-hs256}") String secret,
            @Value("${banking.security.jwt.access-token-expiration-ms:3600000}") long accessTokenExpirationMs,
            @Value("${banking.security.jwt.refresh-token-expiration-ms:86400000}") long refreshTokenExpirationMs,
            @Value("${banking.security.jwt.issuer:banking-framework}") String issuer) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
        this.issuer = issuer;
    }

    /**
     * Generate an access token for a user.
     */
    public String generateAccessToken(String userId, String username, Collection<String> roles) {
        return generateToken(userId, username, roles, accessTokenExpirationMs, "access");
    }

    /**
     * Generate a refresh token.
     */
    public String generateRefreshToken(String userId) {
        return generateToken(userId, null, Collections.emptyList(), refreshTokenExpirationMs, "refresh");
    }

    private String generateToken(String userId, String username, Collection<String> roles,
                                  long expirationMs, String tokenType) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationMs);

        JwtBuilder builder = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(userId)
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("type", tokenType);

        if (username != null) {
            builder.claim("username", username);
        }
        if (roles != null && !roles.isEmpty()) {
            builder.claim("roles", roles);
        }

        return builder.signWith(secretKey).compact();
    }

    /**
     * Validate a JWT token.
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extract user ID (subject) from token.
     */
    public String getUserIdFromToken(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    /**
     * Extract username from token.
     */
    public String getUsernameFromToken(String token) {
        return parseToken(token).getPayload().get("username", String.class);
    }

    /**
     * Extract roles from token.
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Object roles = parseToken(token).getPayload().get("roles");
        if (roles instanceof List) {
            return (List<String>) roles;
        }
        return Collections.emptyList();
    }

    /**
     * Check if a token is expired.
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = parseToken(token).getPayload().getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * Get token expiration date.
     */
    public Instant getExpirationFromToken(String token) {
        return parseToken(token).getPayload().getExpiration().toInstant();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}
