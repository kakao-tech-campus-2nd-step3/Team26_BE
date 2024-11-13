package org.ktc2.cokaen.wouldyouin.review.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.api.ParamDefaults;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventSliceResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewCreateRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewEditRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewResponse;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewSliceResponse;
import org.ktc2.cokaen.wouldyouin.review.application.ReviewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<ReviewSliceResponse>> getReviewsByMemberId(
        @Authorize({MemberType.normal}) MemberIdentifier member,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId) {
        return ApiResponse.ok(
            reviewService.getAllByMemberId(member.id(), lastId, PageRequest.of(page, size)));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<ApiResponseBody<ReviewSliceResponse>> getReviewsByEventId(
        @PathVariable("eventId") Long eventId,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId) {
        return ApiResponse.ok(
            reviewService.getAllByEventId(eventId, lastId, PageRequest.of(page, size)));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponseBody<ReviewResponse>> getReviewByReviewId(
        @PathVariable("reviewId") Long reviewId) {
        return ApiResponse.ok(reviewService.getById(reviewId));
    }

    @GetMapping("events")
    public ResponseEntity<ApiResponseBody<ReviewEventSliceResponse>> getUnreviewedEventsByMemberId(
        @Authorize(MemberType.normal) MemberIdentifier identifier,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId){
        return ApiResponse.ok(reviewService.getUnreviewedEventsByMemberId(identifier.id(), PageRequest.of(page, size), lastId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<ReviewResponse>> createReview(
        @Authorize(MemberType.normal) MemberIdentifier member,
        @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return ApiResponse.created(reviewService.create(member.id(), reviewCreateRequest));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponseBody<ReviewResponse>> updateReview(
        @PathVariable("reviewId") Long reviewId,
        @Authorize(MemberType.normal) MemberIdentifier member,
        @Valid @RequestBody ReviewEditRequest reviewEditRequest) {
        return ApiResponse.ok(reviewService.update(member.id(), reviewId, reviewEditRequest));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteReview(
        @PathVariable("reviewId") Long reviewId,
        @Authorize(MemberType.normal) MemberIdentifier member) {
        reviewService.delete(member.id(), reviewId);
        return ApiResponse.noContent();
    }
}
