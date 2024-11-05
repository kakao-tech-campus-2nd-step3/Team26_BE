package org.ktc2.cokaen.wouldyouin.reservation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.payment.application.PaymentService;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayRequest;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationSliceResponse;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.ktc2.cokaen.wouldyouin.reservation.persist.ReservationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public ReservationSliceResponse getAllByMemberId(Long memberId, Pageable pageable, Long lastId) {
        return getReservationSliceResponse(
            reservationRepository.findByMemberIdOrderByReservationIdDesc(memberId, lastId, pageable), lastId);
    }

    @Transactional(readOnly = true)
    public ReservationSliceResponse getAllByEventId(Long eventId, Pageable pageable, Long lastId) {
        return getReservationSliceResponse(
            reservationRepository.findByEventIdOrderByReservationIdDesc(eventId, lastId, pageable), lastId);
    }

    private ReservationSliceResponse getReservationSliceResponse(Slice<Reservation> reservationSlice, Long lastId) {
        List<ReservationResponse> reservations = reservationSlice.stream().map(ReservationResponse::from).toList();
        if (!reservationSlice.hasContent()) {
            Long id = reservationSlice.getContent().getLast().getId();
            return ReservationSliceResponse.of(reservations, reservationSlice.getSize(), id);
        }
        return ReservationSliceResponse.of(reservations, reservationSlice.getSize(), lastId);
    }

    @Transactional(readOnly = true)
    public ReservationResponse getById(Long id) {
        Reservation target = reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("reservation "));
        return ReservationResponse.from(target);
    }

    @Transactional
    public KakaoPayResponse create(Long memberId, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.save(reservationRequest.toEntity(
            memberService.getByIdOrThrow(memberId),
            eventService.getByIdOrThrow(reservationRequest.getEventId())));
        eventService.decreaseLeftSeat(reservation.getEvent().getId(), reservationRequest.getQuantity());
        return paymentService.createPayment(KakaoPayRequest.from(reservation));
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("reservation "));
        reservationRepository.deleteById(id);
    }
}