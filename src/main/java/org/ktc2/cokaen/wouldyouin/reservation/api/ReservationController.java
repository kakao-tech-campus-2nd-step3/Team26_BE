package org.ktc2.cokaen.wouldyouin.reservation.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<List<ReservationResponse>>> getReservations() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reservationService.getAll()));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponseBody<ReservationResponse>> getReservation(
        @PathVariable Long reservationId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reservationService.getById(reservationId)));
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponseBody<List<ReservationResponse>>> getReservationsByMemberId(
        @PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reservationService.getAllByMemberId(memberId)));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<ApiResponseBody<List<ReservationResponse>>> getReservationsByEventId(
        @PathVariable Long eventId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reservationService.getAllByEventId(eventId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<KakaoPayResponse>> createReservation(
        @RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponseBody<>(true, reservationService.create(reservationRequest)));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteReservation(
        @PathVariable Long reservationId) {
        reservationService.delete(reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new ApiResponseBody<>(true, null));
    }
}