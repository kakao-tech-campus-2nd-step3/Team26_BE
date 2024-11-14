package org.ktc2.cokaen.wouldyouin.review.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewCreateRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewEditRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewResponse;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewSliceResponse;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;
import org.ktc2.cokaen.wouldyouin.review.persist.ReviewRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventService eventService;
    private final MemberService memberService;
    private final ReservationService reservationService;

    @Transactional(readOnly = true)
    public ReviewResponse getById(Long reviewId) {
        return ReviewResponse.from(getByIdOrThrow(reviewId));
    }

    @Transactional(readOnly = true)
    public ReviewSliceResponse getAllByMemberId(Long memberId, Pageable pageable, Long oldLastId) {
        Slice<Review> reviews = reviewRepository.findByMemberIdOrderByReviewIdDesc(memberId,
            oldLastId, pageable);
        Long newLastId = getLastId(reviews, oldLastId);
        return ReviewSliceResponse.from(reviews, reviews.getSize(), newLastId);
    }

    @Transactional(readOnly = true)
    public ReviewSliceResponse getAllByEventId(Long eventId, Pageable pageable, Long oldLastId) {
        Slice<Review> reviews = reviewRepository.findByEventIdOrderByReviewIdDesc(eventId,
            oldLastId, pageable);
        Long newLastId = getLastId(reviews, oldLastId);
        return ReviewSliceResponse.from(reviews, reviews.getSize(), newLastId);
    }

    private Long getLastId(Slice<Review> reviews, Long oldLastId) {
        if (reviews.hasContent()) {
            return reviews.getContent().getLast().getId();
        }
        return oldLastId;
    }

    @Transactional
    public ReviewEventSliceResponse getUnreviewedEventsByMemberId(Long memberId, Pageable pageable,
        Long beforeLastId) {
        Slice<Event> unreviewedEvents = reviewRepository.findUnreviewedEventsByMemberId(memberId,
            beforeLastId, pageable);
        Long newLastId = EventService.getLastId(unreviewedEvents, beforeLastId);
        List<ReviewEventResponse> responses = unreviewedEvents.stream()
            .map(ReviewEventResponse::from).toList();
        return ReviewEventSliceResponse.from(responses, unreviewedEvents.getSize(), newLastId);
    }

    @Transactional
    public ReviewResponse create(Long memberId, ReviewCreateRequest reviewCreateRequest) {
        reservationService.validateByMemberIdAndEventId(memberId, reviewCreateRequest.getEventId());
        Review review = reviewRepository.save(
            reviewCreateRequest.toEntity(
                memberService.getByIdOrThrow(memberId),
                eventService.getByIdOrThrow(reviewCreateRequest.getEventId())));
        return ReviewResponse.from(review);
    }

    @Transactional
    public ReviewResponse update(Long memberId, Long reviewId,
        ReviewEditRequest reviewEditRequest) {
        Review target = getByIdOrThrow(reviewId);
        validateMemberId(memberId, target);
        target.updateFrom(reviewEditRequest);
        return ReviewResponse.from(target);
    }

    @Transactional
    public void delete(Long memberId, Long reviewId) {
        validateMemberId(memberId, getByIdOrThrow(reviewId));
        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public Review getByIdOrThrow(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 리뷰를 찾을 수 없습니다."));
    }

    private void validateMemberId(Long memberId, Review review) {
        if (!memberId.equals(review.getMember().getId())) {
            throw new UnauthorizedException("member ID가 리뷰의 member ID와 일치하지 않습니다.");
        }
    }
}
