package com.kakaotechcampus.journey_planner.application.auth.jwt;


import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtProvider {
    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration-in-ms}") long expiration,
            @Value("${jwt.refresh-token-expiration-in-ms}") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = expiration;
        this.refreshTokenValidityInMilliseconds = refreshTokenExpiration;
    }
    public String generateAccessToken(Member member) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .subject(member.getEmail())
                .claim("tokenType", "access")
                .claim("contact", member.getContact())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Member member) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .subject(member.getEmail())
                .claim("tokenType", "refresh") // 토큰 타입을 'refresh'로 명시
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmailFromAccessToken(String accessToken) {
        Claims claims = parseClaims(accessToken);
        validateTokenType(claims, "access");
        validateTokenExpiration(claims);
        return claims.getSubject();
    }

    public String extractEmailFromRefreshToken(String refreshToken) {
        Claims claims = parseClaims(refreshToken);
        validateTokenType(claims, "refresh");
        validateTokenExpiration(claims);
        return claims.getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateTokenType(Claims claims, String expectedType) {
        String actualType = claims.get("tokenType", String.class);
        if (!expectedType.equals(actualType)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }

    private void validateTokenExpiration(Claims claims) {
        if (claims.getExpiration().before(new Date())) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

}