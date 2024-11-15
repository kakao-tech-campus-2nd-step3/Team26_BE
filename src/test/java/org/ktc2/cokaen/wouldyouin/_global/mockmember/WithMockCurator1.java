package org.ktc2.cokaen.wouldyouin._global.mockmember;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Retention(RetentionPolicy.RUNTIME)
@WithMockCustomUser(memberId = curator1.id, memberType = MemberType.curator)
public @interface WithMockCurator1 {
    // TODO: mockMember 패키지명 변경
}
