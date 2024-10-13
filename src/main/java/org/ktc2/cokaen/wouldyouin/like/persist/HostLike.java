package org.ktc2.cokaen.wouldyouin.like.persist;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HostLike extends Like<Host> {

    @Builder
    protected HostLike(Host targetMember, Member member) {
        super(targetMember, MemberType.host, member);
    }
}
