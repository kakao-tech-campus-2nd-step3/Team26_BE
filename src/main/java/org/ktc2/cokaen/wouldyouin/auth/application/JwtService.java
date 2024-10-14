package org.ktc2.cokaen.wouldyouin.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
//TODO: 커스텀 예외 필요
public class JwtService {

    @Value("${jwt.access.expiration}")
    private long accessExpiration;
    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    private final String CLAIM_ID = "memberId";
    private final String CLAIM_TYPE = "memberType";
    private final String BEARER = "Bearer ";

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Access Token 생성
    public String createAccessToken(Long memberId, MemberType memberType) {
        Claims claims = Jwts.claims();
        claims.put(CLAIM_ID, memberId);
        claims.put(CLAIM_TYPE, memberType);

        ZoneOffset offset = ZoneOffset.UTC;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenValidity = now.plusSeconds(accessExpiration);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.toInstant(offset)))
            .setExpiration(Date.from(tokenValidity.toInstant(offset)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // Authorization 필드의 토큰에서 MemberIdentifier를 가져옴
    public MemberIdentifier getMemberIdentifierFrom(String authorizationHeader) {
        // 토큰 검증
        String actualToken = removePrefixFrom(authorizationHeader);
        validateToken(actualToken);

        //토큰에서 정보 추출
        Claims claims = parseClaims(actualToken);
        return new MemberIdentifier(
            claims.get(CLAIM_ID, Long.class),
            MemberType.valueOf(claims.get(CLAIM_TYPE, String.class)));
    }

    public boolean hasPrefix(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(BEARER);
    }

    // 토큰 접두사 제거
    private String removePrefixFrom(String authorizationHeader) {
        if (!hasPrefix(authorizationHeader)) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }

        // 'Bearer ' 제거 후 실제 JWT 토큰만 추출 및 토큰 검증
        return authorizationHeader.substring(BEARER.length());
    }

    // 토큰 검증
    private void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT claims string is empty.");
        }
    }

    // JWT Claims 추출
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}