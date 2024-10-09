package org.ktc2.cokaen.wouldyouin.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//TODO: 토큰처리 로직추가
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String EMAIL_CLAIM = "Email";
    private static final String MEMBER_TYPE_CLAIM = "MemberType";
    private static final String BEARER = "Bearer ";

    private final BaseMemberRepository baseMemberRepository;

    // AccessToken 생성
    public String createAccessToken(BaseMember member) {
        return JWT.create()
            .withSubject(ACCESS_TOKEN_SUBJECT)
            .withExpiresAt(new Date(System.currentTimeMillis() + accessExpiration))
            .withClaim(EMAIL_CLAIM, member.getEmail())
            .withClaim(MEMBER_TYPE_CLAIM, member.getMemberType().toString())
            .sign(Algorithm.HMAC256(secretKey));
    }

    public String getTokenFromMember(Member member) {
        return member.getId().toString();
    }

    public MemberIdentifier getMemberIdentifierFrom(String token) {
        try {
            if (token == null || !token.startsWith(BEARER)) {
                throw new RuntimeException("Authorization header is missing or invalid");
            }

            // 'Bearer ' 제거 후 실제 JWT 토큰만 추출
            String actualToken = token.substring(BEARER.length());

            // 토큰을 검증하고 디코딩
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .build()
                .verify(actualToken);

            // 토큰에서 이메일과 회원 타입 클레임을 추출
            String email = decodedJWT.getClaim(EMAIL_CLAIM).asString();
            String memberType = decodedJWT.getClaim(MEMBER_TYPE_CLAIM).asString();

            // 이메일로 회원 정보를 조회
            BaseMember member = baseMemberRepository.findByEmail(email)
                //TODO: 커스텀 예외 필요
                .orElseThrow(() -> new IllegalArgumentException("Invalid token: no matching user"));

            // MemberIdentifier 객체를 반환
            return new MemberIdentifier(member.getId(), MemberType.valueOf(memberType));

        } catch (TokenExpiredException e) {
            // 토큰이 만료된 경우의 처리
            throw new RuntimeException("Token has expired", e);
        } catch (JWTDecodeException e) {
            // 토큰 디코딩 실패 시의 처리
            throw new RuntimeException("Invalid token format", e);
        } catch (Exception e) {
            // 그 외 다른 예외 처리
            throw new RuntimeException("Token validation failed", e);
        }
    }
}
