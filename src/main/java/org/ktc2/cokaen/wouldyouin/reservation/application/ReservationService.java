package org.ktc2.cokaen.wouldyouin.reservation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.ktc2.cokaen.wouldyouin.reservation.persist.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll().stream()
            .map(ReservationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllByMemberId(Long memberId) {
        return reservationRepository.findByMemberId(memberId).stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllByEventId(Long eventId) {
        return reservationRepository.findByEventId(eventId).stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public ReservationResponse getById(Long id) {
        Reservation target = reservationRepository.findById(id).orElseThrow(RuntimeException::new);
        return ReservationResponse.from(target);
    }

    @Transactional
    public ReservationResponse create(ReservationRequest reservationRequest) {
        return ReservationResponse.from(
            reservationRepository.save(reservationRequest.toEntity()));
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.findById(id).orElseThrow(RuntimeException::new);
        reservationRepository.deleteById(id);
    }
}