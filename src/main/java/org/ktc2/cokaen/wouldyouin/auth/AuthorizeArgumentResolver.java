package org.ktc2.cokaen.wouldyouin.auth;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthorizeArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authorize.class)
            && parameter.getParameterType().isAssignableFrom(MemberIdentifier.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        // Authorization 헤더에서 Bearer 토큰 추출
        String authorizationHeader = webRequest.getHeader("Authorization");

        // 토큰으로부터 MemberIdentifier(id와 타입 페어) 추출하여 Authorize가 요구한 멤버타입과 일치하는지 검증
        // ID의 실제 검증은 컨트롤러로 넘어간 후 MemberService에서 진행
        MemberIdentifier identifier = jwtService.getMemberIdentifierFrom(authorizationHeader);
        MemberType memberType = Objects.requireNonNull(parameter.getParameterAnnotation(Authorize.class)).value(); //Authorize가 요구한 멤버 타입 추출
        if (memberType != MemberType.any && memberType != identifier.type()) {
            //TODO: 커스텀 예외 추가 필요
            throw new RuntimeException("Invalid member type");
        }

        return identifier;

    }
}
