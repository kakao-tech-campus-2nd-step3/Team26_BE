package org.ktc2.cokaen.wouldyouin._global.mockmember;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.normal1;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Retention(RetentionPolicy.RUNTIME)
@WithMockCustomUser(memberId = normal1.id, memberType = MemberType.normal)
public @interface WithMockMember1 {

}