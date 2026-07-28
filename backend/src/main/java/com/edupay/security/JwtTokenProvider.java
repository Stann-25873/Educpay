package com.edupay.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final String issuer;
    private final String audience;
    private final Duration accessTokenTtl;
    private final Duration refreshTokenTtl;
    private final SecretKey signingKey;

    public JwtTokenProvider(
            @Value("${edupay.jwt.issuer:edupay}") String issuer,
            @Value("${edupay.jwt.audience:edupay}") String audience,
            @Value("${edupay.jwt.access-token-ttl-seconds:900}") long accessTokenTtlSeconds,
            @Value("${edupay.jwt.refresh-token-ttl-seconds:2592000}") long refreshTokenTtlSeconds,
            @Value("${edupay.jwt.secret-base64:${EDUPAY_JWT_SECRET_BASE64:eW91ci0yNTYtYml0LXNlY3JldC1rZXktY2hhbmdlLW1lLWluLXByb2R1Y3Rpb24=}}") String secretBase64
    ) {
        this.issuer = issuer;
        this.audience = audience;
        this.accessTokenTtl = Duration.ofSeconds(accessTokenTtlSeconds);
        this.refreshTokenTtl = Duration.ofSeconds(refreshTokenTtlSeconds);
        byte[] keyBytes = Base64.getDecoder().decode(secretBase64);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String subject, String tenantId, Map<String, Object> extraClaims) {
        return buildToken(subject, tenantId, extraClaims, accessTokenTtl, "access");
    }

    public String generateRefreshToken(String subject, String tenantId, Map<String, Object> extraClaims) {
        return buildToken(subject, tenantId, extraClaims, refreshTokenTtl, "refresh");
    }

    private String buildToken(
            String subject,
            String tenantId,
            Map<String, Object> extraClaims,
            Duration ttl,
            String tokenType
    ) {
        Instant now = Instant.now();
        Instant expiry = now.plus(ttl);

        return Jwts.builder()
                .claims(extraClaims == null ? Map.of() : extraClaims)
                .subject(subject)
                .issuer(issuer)
                .audience().add(audience).and()
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("tenant_id", tenantId)
                .claim("typ", tokenType)
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            parseAllClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Claims parseAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractTenantId(String token) {
        Object tenant = parseAllClaims(token).get("tenant_id");
        return tenant == null ? null : tenant.toString();
    }

    public String extractSubject(String token) {
        return parseAllClaims(token).getSubject();
    }

    public String extractTokenType(String token) {
        Object typ = parseAllClaims(token).get("typ");
        return typ == null ? null : typ.toString();
    }
}