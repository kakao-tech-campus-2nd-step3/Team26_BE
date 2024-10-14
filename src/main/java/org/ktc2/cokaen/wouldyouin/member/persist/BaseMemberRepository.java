package org.ktc2.cokaen.wouldyouin.member.persist;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseMemberRepository extends JpaRepository<BaseMember, Long> {

    Optional<BaseMember> findByEmail(String email);
}
