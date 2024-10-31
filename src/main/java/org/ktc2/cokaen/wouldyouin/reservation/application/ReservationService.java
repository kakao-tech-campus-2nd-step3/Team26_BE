package org.ktc2.cokaen.wouldyouin.reservation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayRequest;
import org.ktc2.cokaen.wouldyouin.payment.application.PaymentService;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
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
    private final PaymentService paymentService;
    private final MemberService memberService;
    private final EventService eventService;

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
    public KakaoPayResponse create(ReservationRequest reservationRequest) {

        Reservation reservation = reservationRequest.toEntity();
        reservation.setMember(memberService.getByIdOrThrow(reservationRequest.getMemberId()));
        reservation.setEvent(eventService.getByIdOrThrow(reservationRequest.getEventId()));
        reservationRepository.save(reservation);
        return paymentService.createPayment(KakaoPayRequest.from(reservation));
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.findById(id).orElseThrow(RuntimeException::new);
        reservationRepository.deleteById(id);
    }
}