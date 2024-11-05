package org.ktc2.cokaen.wouldyouin.reservation.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationSliceResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<ReservationSliceResponse>> getReservationsByMemberId(
        Long memberId, //Todo : 테스트이후 Authorize 추가
        @RequestParam(defaultValue = "${spring.controller.pageable.default-page}") Integer page,
        @RequestParam(defaultValue = "${spring.controller.pageable.default-page-size}") Integer size,
        @RequestParam(defaultValue = "${spring.controller.pageable.default-last-id}") Long lastId) {
        return ApiResponse.ok(reservationService.getAllByMemberId(memberId, PageRequest.of(page, size), lastId));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<ApiResponseBody<ReservationSliceResponse>> getReservationsByEventId(
        @PathVariable Long eventId,
        @RequestParam(defaultValue = "${spring.controller.pageable.default-page}") Integer page,
        @RequestParam(defaultValue = "${spring.controller.pageable.default-page-size}") Integer size,
        @RequestParam(defaultValue = "${spring.controller.pageable.default-last-id}") Long lastId) {
        return ApiResponse.ok(reservationService.getAllByEventId(eventId, PageRequest.of(page, size), lastId));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponseBody<ReservationResponse>> getReservationById(
        @PathVariable Long reservationId) {
        return ApiResponse.ok(reservationService.getById(reservationId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<KakaoPayResponse>> createReservation(
        @Valid @RequestBody ReservationRequest reservationRequest,
        MemberIdentifier memberIdentifier) {
        return ApiResponse.created(reservationService.create(memberIdentifier.id(), reservationRequest));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteReservation(
        @PathVariable Long reservationId) {
        reservationService.delete(reservationId);
        return ApiResponse.noContent();
    }
}