package org.ktc2.cokaen.wouldyouin.repository.member;

import org.ktc2.cokaen.wouldyouin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
