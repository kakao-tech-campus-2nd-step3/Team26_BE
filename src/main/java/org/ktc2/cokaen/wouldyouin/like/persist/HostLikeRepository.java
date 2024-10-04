package org.ktc2.cokaen.wouldyouin.like.persist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostLikeRepository extends JpaRepository<HostLike, Long> {

    public List<HostLike> findByMemberId(Long memberId);
}
