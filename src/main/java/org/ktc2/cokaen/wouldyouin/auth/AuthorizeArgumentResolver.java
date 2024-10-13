package org.ktc2.cokaen.wouldyouin.auth;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.auth.persist.CustomUserDetails;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
//TODO: 커스텀 예외 추가 필요
public class AuthorizeArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authorize.class)
            && parameter.getParameterType().isAssignableFrom(MemberIdentifier.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        List<MemberType> requiredTypes = getRequiredMemberTypes(parameter);
        MemberIdentifier authorizedId = getAuthorizedMemberIdFrom(SecurityContextHolder.getContext().getAuthentication());
        validateMemberType(requiredTypes, authorizedId.type());
        return authorizedId;
    }

    // 토큰으로 인증된 사용자 정보 얻기
    private MemberIdentifier getAuthorizedMemberIdFrom(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("No authentication found");
        }
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getIdentifier();
        }
        throw new RuntimeException("No CustomUserDetails found");
    }

    // @Authorize 애너테이션이 요구한 멤버 타입 추출
    private List<MemberType> getRequiredMemberTypes(MethodParameter parameter) {
        return List.of(Objects.requireNonNull(parameter.getParameterAnnotation(Authorize.class)).value());
    }

    // 사용자 유형 검증
    private void validateMemberType(List<MemberType> required, MemberType actual) {
        if (actual == MemberType.admin || required.contains(actual)) {
            return;
        }
        throw new RuntimeException("요구된 멤버 형식과 실제 형식이 다릅니다.");
    }
}
