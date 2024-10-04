package org.ktc2.cokaen.wouldyouin.like.persist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuratorLikeRepository extends JpaRepository<CuratorLike, Long> {

    public List<CuratorLike> findByMemberId(Long memberId);
}
