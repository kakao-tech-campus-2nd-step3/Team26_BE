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

    @Query("SELECT DISTINCT e FROM Event e " +
        "JOIN FETCH e.reservations r " +
        "WHERE r.member.id = :memberId " +
        "AND e.id < :lastId " +
        "AND e.id NOT IN (SELECT rv.event.id FROM Review rv WHERE rv.member.id = :memberId)")
    Slice<Event> findUnreviewedEventsByMemberId(Long memberId, Long lasId, Pageable pageable);
}
