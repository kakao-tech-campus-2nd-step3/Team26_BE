package org.ktc2.cokaen.wouldyouin.global.mockMember;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long memberId() default 1L;
    MemberType memberType();
}
