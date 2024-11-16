package org.ktc2.cokaen.wouldyouin.reservation.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.payment.application.PaymentService;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayRequest;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.KakaoPayReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationSliceResponse;
import org.ktc2.cokaen.wouldyouin.reservation.exception.ReservationNotFoundForReviewException;
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
    public ReservationResponse getById(Long id) {
        return ReservationResponse.from(getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public ReservationSliceResponse getAllByMemberId(MemberIdentifier identifier, Pageable pageable, Long oldLastId) {
        Slice<Reservation> reservations =
            reservationRepository.findByMemberIdOrderByReservationIdDesc(identifier.id(), oldLastId, pageable);
        Long newLastId = getLastId(reservations, oldLastId);
        return ReservationSliceResponse.from(reservations, reservations.getSize(), newLastId);
    }

    @Transactional(readOnly = true)
    public ReservationSliceResponse getAllByEventId(
        MemberIdentifier identifier, Long eventId, Pageable pageable, Long oldLastId) {
        eventService.validateHostId(identifier, eventService.getByIdOrThrow(eventId));
        Slice<Reservation> reservations = reservationRepository.findByEventIdOrderByReservationIdDesc(
            eventId, oldLastId, pageable);
        Long newLastId = getLastId(reservations, oldLastId);
        return ReservationSliceResponse.from(reservations, reservations.getSize(), newLastId);
    }

    @Transactional
    public KakaoPayReservationResponse create(
        MemberIdentifier identifier, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.save(reservationRequest.toEntity(
            memberService.getByIdOrThrow(identifier.id()),
            eventService.getByIdOrThrow(reservationRequest.getEventId()))
        );
        eventService.decreaseLeftSeat(reservation.getEvent().getId(),
            reservationRequest.getQuantity());
        KakaoPayResponse kakaoPayResponse = paymentService.createPayment(
            KakaoPayRequest.from(reservation));
        ReservationResponse reservationResponse = ReservationResponse.from(reservation);
        return KakaoPayReservationResponse.from(reservationResponse, kakaoPayResponse);
    }

    @Transactional
    public ReservationResponse createTest(
        MemberIdentifier identifier, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.save(reservationRequest.toEntity(
            memberService.getByIdOrThrow(identifier.id()),
            eventService.getByIdOrThrow(reservationRequest.getEventId())));
        eventService.decreaseLeftSeat(reservation.getEvent().getId(),
            reservationRequest.getQuantity());
        return ReservationResponse.from(reservation);
    }

    @Transactional
    public void delete(MemberIdentifier identifier, Long reservationId) {
        validateMemberId(identifier.id(), getByIdOrThrow(reservationId));
        reservationRepository.deleteById(reservationId);
    }

    @Transactional(readOnly = true)
    public void validateByMemberIdAndEventId(Long memberId, Long eventId) {
        if (reservationRepository.findByMemberIdAndEventId(memberId, eventId).isEmpty()) {
            throw new ReservationNotFoundForReviewException("해당 이벤트에 대한 리뷰를 작성할 수 없습니다.");
        }
    }

    private Reservation getByIdOrThrow(Long id) {
        return reservationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 예약을 찾을 수 없습니다."));
    }

    private Long getLastId(Slice<Reservation> reservations, Long oldLastId) {
        if (reservations.hasContent()) {
            return reservations.getContent().getLast().getId();
        }
        return oldLastId;
    }

    private void validateMemberId(Long memberId, Reservation reservation) {
        if (!memberId.equals(reservation.getMember().getId())) {
            throw new UnauthorizedException("사용자 ID가 예약한 사용자 ID와 일치하지 않습니다.");
        }
    }
}