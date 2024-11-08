package org.ktc2.cokaen.wouldyouin.global.mockMember;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.ktc2.cokaen.wouldyouin.global.TestData;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Retention(RetentionPolicy.RUNTIME)
@WithMockCustomUser(memberId = 5L, memberType = MemberType.curator)
public @interface WithMockCurator {

}
