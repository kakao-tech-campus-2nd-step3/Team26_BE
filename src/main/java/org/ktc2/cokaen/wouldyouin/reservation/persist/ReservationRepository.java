package org.ktc2.cokaen.wouldyouin.reservation.persist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findByMemberId(Long memberId);
    public List<Reservation> findByEventId(Long eventId);
}
