package org.ktc2.cokaen.wouldyouin.repository;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    public List<Review> findByMemberId(Long memberId);

    public List<Review> findByEventId(Long eventId);
}
