package org.ktc2.cokaen.wouldyouin.review.persist;

import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT R FROM Review R JOIN FETCH R.member JOIN FETCH R.event "
        + "WHERE R.member.id = :memberId AND R.id > :lastId "
        + "ORDER BY R.id DESC")
    Slice<Review> findByMemberIdOrderByReviewIdDesc(Long memberId, Long lastId, Pageable pageable);

    @Query("SELECT R FROM Review R JOIN FETCH R.member JOIN FETCH R.event "
        + "WHERE R.event.id = :eventId AND R.id > :lastId "
        + "ORDER BY R.id DESC")
    Slice<Review> findByEventIdOrderByReviewIdDesc(Long eventId, Long lastId, Pageable pageable);

    @Query("select r, e from Review r Join fetch r.event e "
        + "where r.member.id = :memberId "
        + "And e.id < :lastId "
        + "And e.id not in (select rv.event.id from Review rv where rv.member.id = :memberId) ")
    Slice<Event> findUnreviewedEventsByMemberId(Long memberId, Long lastId, Pageable pageable);
}
