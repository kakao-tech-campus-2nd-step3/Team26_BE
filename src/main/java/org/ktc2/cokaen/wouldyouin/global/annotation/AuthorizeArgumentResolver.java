package org.ktc2.cokaen.wouldyouin.global.annotation;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.global.util.JwtManager;
import org.ktc2.cokaen.wouldyouin.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthorizeArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtManager jwtManager;
    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authorize.class)
            && parameter.getParameterType().isAssignableFrom(UUID.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        // Authorization 헤더에서 Bearer 토큰 추출
        String authorizationHeader = webRequest.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }

        // ID의 실제 검증은 컨트롤러로 넘어간 후 MemberService에서 진행
        return jwtManager.getMemberFromToken(authorizationHeader.substring(7));
    }
}
