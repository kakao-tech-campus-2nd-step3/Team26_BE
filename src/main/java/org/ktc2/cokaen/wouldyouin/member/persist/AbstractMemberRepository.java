package org.ktc2.cokaen.wouldyouin.member.persist;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractMemberRepository extends JpaRepository<AbstractMember, Long> {

    Optional<AbstractMember> findByEmail(String email);

    Optional<AbstractMember> findByRefreshToken(String refreshToken);

    Optional<AbstractMember> findByAccountTypeAndSocialId(AccountType accountType, String socialId);
}
