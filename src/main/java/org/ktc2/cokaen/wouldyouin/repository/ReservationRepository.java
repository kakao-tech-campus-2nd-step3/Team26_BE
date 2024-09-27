package org.ktc2.cokaen.wouldyouin.repository;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findByMemberId(Long memberId);
    public List<Reservation> findByEventId(Long eventId);
}
