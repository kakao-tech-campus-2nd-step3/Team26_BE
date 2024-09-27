package org.ktc2.cokaen.wouldyouin.repository.member;

import org.ktc2.cokaen.wouldyouin.domain.member.AbstractMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractMemberRepository extends JpaRepository<AbstractMember, Long> {
}
