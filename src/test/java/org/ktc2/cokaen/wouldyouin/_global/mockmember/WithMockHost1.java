package org.ktc2.cokaen.wouldyouin._global.mockmember;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.host1;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Retention(RetentionPolicy.RUNTIME)
@WithMockCustomUser(memberId = host1.id, memberType = MemberType.host)
public @interface WithMockHost1 {

}
