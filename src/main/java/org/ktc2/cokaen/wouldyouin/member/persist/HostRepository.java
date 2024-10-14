package org.ktc2.cokaen.wouldyouin.member.persist;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findByEmailAndHashedPassword(String email, String hashedPassword);
}
