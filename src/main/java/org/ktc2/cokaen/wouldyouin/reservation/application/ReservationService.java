package org.ktc2.cokaen.wouldyouin.reservation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.payment.application.PaymentService;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayRequest;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationSliceResponse;
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

    @Transactional
    public Reservation getByIdOrThrow(Long id) {
        return reservationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 예약을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public ReservationResponse getById(Long id) {
        return ReservationResponse.from(getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public ReservationSliceResponse getAllByMemberId(Long memberId, Pageable pageable, Long oldLastId) {
        Slice<Reservation> reservations = reservationRepository.findByMemberIdOrderByReservationIdDesc(memberId, oldLastId, pageable);
        Long newLastId = getLastId(reservations, oldLastId);
        return ReservationSliceResponse.from(reservations, reservations.getSize(), newLastId);
    }

    @Transactional(readOnly = true)
    public ReservationSliceResponse getAllByEventId(Long hostId, Long eventId, Pageable pageable, Long oldLastId) {
        eventService.validateHostId(hostId, eventService.getByIdOrThrow(eventId));
        Slice<Reservation> reservations = reservationRepository.findByEventIdOrderByReservationIdDesc(eventId, oldLastId, pageable);
        Long newLastId = getLastId(reservations, oldLastId);
        return ReservationSliceResponse.from(reservations, reservations.getSize(), newLastId);
    }

    private Long getLastId(Slice<Reservation> reservations, Long oldLastId) {
        if (reservations.hasContent()) {
            return reservations.getContent().getLast().getId();
        }
        return oldLastId;
    }

    @Transactional
    public KakaoPayResponse create(Long memberId, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.save(reservationRequest.toEntity(
            memberService.getByIdOrThrow(memberId),
            eventService.getByIdOrThrow(reservationRequest.getEventId()))
        );
        eventService.decreaseLeftSeat(reservation.getEvent().getId(), reservationRequest.getQuantity());
        return paymentService.createPayment(KakaoPayRequest.from(reservation));
    }

    @Transactional
    public void delete(Long memberId, Long reservationId) {
        validateMemberId(memberId, getByIdOrThrow(reservationId));
        reservationRepository.deleteById(reservationId);
    }

    private void validateMemberId(Long memberId, Reservation reservation) {
        if (!memberId.equals(reservation.getMember().getId())) {
            throw new UnauthorizedException("member ID가 예약의 member ID와 일치하지 않습니다.");
        }
    }
}