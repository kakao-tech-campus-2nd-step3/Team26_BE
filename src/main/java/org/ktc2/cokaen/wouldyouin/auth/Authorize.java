package org.ktc2.cokaen.wouldyouin.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    MemberType[] value();
}
