package org.ktc2.cokaen.wouldyouin.like.persist;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CuratorLike extends Like<Curator>{

    @Builder
    protected CuratorLike(Curator targetMember, Member member) {
        super(targetMember, MemberType.curator, member);
    }
}
