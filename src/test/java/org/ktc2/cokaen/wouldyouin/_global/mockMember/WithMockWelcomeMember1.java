package org.ktc2.cokaen.wouldyouin._global.mockMember;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.welcome1;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Retention(RetentionPolicy.RUNTIME)
@WithMockCustomUser(memberId = welcome1.id, memberType = MemberType.welcome)
public @interface WithMockWelcomeMember1 {

}
