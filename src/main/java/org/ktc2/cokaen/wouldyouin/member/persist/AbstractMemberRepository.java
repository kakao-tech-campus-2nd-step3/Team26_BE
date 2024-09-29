package org.ktc2.cokaen.wouldyouin.member.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractMemberRepository extends JpaRepository<AbstractMember, Long> {
}
