package org.ktc2.cokaen.wouldyouin.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

/**
 * 특정 API 엔드포인트에 인가된 사용자만 접근이 필요한 경우, 이 애너테이션과 함께 MemberIdentifier 를 파라미터에
 * 지정해주면 인가된 사용자 유형의 사용자만 접근할 수 있도록 할 수 있다.<br>
 * 예시 1<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;
 *      LikeController 의 모든 엔드포인트는 일반 사용자만 접근 가능해야 하므로<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;
 *      Authorize(MemberType.normal) MemberIdentifier identifier 를 파라미터로 받음<br>
 * 예시 2<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;
 *      MemberController 의 사용자 삭제 엔드포인트는 모든 인가된 사용자가 접근 가능해야 하므로<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;
 *      &#064;Authorize({MemberType.normal, MemberType.host, MemberType.curator}) MemberIdentifier identifier 를 파라미터로 받음<br>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    MemberType[] value();
}
