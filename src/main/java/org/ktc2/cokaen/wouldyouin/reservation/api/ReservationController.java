package org.ktc2.cokaen.wouldyouin.reservation.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.api.ParamDefaults;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.KakaoPayReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationSliceResponse;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
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
        @Authorize({MemberType.normal, MemberType.curator}) MemberIdentifier identifier,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue =  ParamDefaults.LAST_ID) Long lastId) {
        return ApiResponse.ok(reservationService.getAllByMemberId(identifier, PageRequest.of(page, size), lastId));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<ApiResponseBody<ReservationSliceResponse>> getReservationsByEventId(
        @Authorize(MemberType.host) MemberIdentifier identifier,
        @PathVariable Long eventId,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId) {
        return ApiResponse.ok(reservationService.getAllByEventId(identifier, eventId, PageRequest.of(page, size), lastId));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponseBody<ReservationResponse>> getReservationById(
        @PathVariable Long reservationId) {
        return ApiResponse.ok(reservationService.getById(reservationId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<KakaoPayReservationResponse>> createReservation(
        @Valid @RequestBody ReservationRequest reservationRequest,
        @Authorize({MemberType.normal, MemberType.curator}) MemberIdentifier identifier) {
        return ApiResponse.created(reservationService.create(identifier, reservationRequest));
    }

    @PostMapping("/test")
    public ResponseEntity<ApiResponseBody<ReservationResponse>> createTestReservation(
        @Valid @RequestBody ReservationRequest reservationRequest,
        @Authorize({MemberType.normal, MemberType.curator}) MemberIdentifier identifier) {
        return ApiResponse.created(reservationService.createTest(identifier, reservationRequest));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteReservation(
        @PathVariable Long reservationId,
        @Authorize({MemberType.normal, MemberType.curator}) MemberIdentifier identifier) {
        reservationService.delete(identifier, reservationId);
        return ApiResponse.noContent();
    }
}