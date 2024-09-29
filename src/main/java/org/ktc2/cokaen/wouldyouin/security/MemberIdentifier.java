package org.ktc2.cokaen.wouldyouin.security;

import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

public record MemberIdentifier(Long id, MemberType type) {

}
