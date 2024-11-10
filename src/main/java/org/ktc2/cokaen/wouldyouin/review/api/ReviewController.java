package org.ktc2.cokaen.wouldyouin.review.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.review.application.ReviewService;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewCreateRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewEditRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // ToDo: ApiResponse 정적메서드 활용

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponseBody<List<ReviewResponse>>> getReviewsByMemberId(
        @PathVariable("memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reviewService.getAllByMemberId(memberId)));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<List<ReviewResponse>>> getReviewsByEventId(
        @PathVariable("eventId") Long eventId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reviewService.getAllByEventId(eventId)));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponseBody<ReviewResponse>> getReviewByReviewId(
        @PathVariable("reviewId") Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reviewService.getById(reviewId)));
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<ReviewResponse>> createReview(
        @PathVariable("eventId") Long eventId,
        @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reviewService.create(eventId, reviewCreateRequest)));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponseBody<ReviewResponse>> updateReview(
        @PathVariable("reviewId") Long reviewId,
        @RequestBody ReviewEditRequest reviewEditRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, reviewService.update(reviewId, reviewEditRequest)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteReview(
        @PathVariable("reviewId") Long reviewId) {
        reviewService.delete(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new ApiResponseBody<>(true, null));
    }

}
