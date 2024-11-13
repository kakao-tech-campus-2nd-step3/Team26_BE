package org.ktc2.cokaen.wouldyouin.reservation.persist;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT R FROM Reservation R JOIN FETCH R.member JOIN FETCH R.event "
        + "WHERE R.member.id = :memberId AND R.id > :lastId "
        + "ORDER BY R.id DESC")
    Slice<Reservation> findByMemberIdOrderByReservationIdDesc(Long memberId, Long lastId, Pageable pageable);

    @Query("SELECT R FROM Reservation R JOIN FETCH R.member JOIN FETCH R.event "
        + "WHERE R.event.id = :eventId AND R.id > :lastId "
        + "ORDER BY R.id DESC")
    Slice<Reservation> findByEventIdOrderByReservationIdDesc(Long eventId, Long lastId, Pageable pageable);

    Reservation findByMemberIdAndEventId(Long memberId, Long eventId);
}