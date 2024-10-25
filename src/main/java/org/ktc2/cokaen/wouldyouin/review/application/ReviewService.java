package org.ktc2.cokaen.wouldyouin.review.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.review.application.dto.ReviewCreateRequest;
import org.ktc2.cokaen.wouldyouin.review.application.dto.ReviewEditRequest;
import org.ktc2.cokaen.wouldyouin.review.application.dto.ReviewResponse;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;
import org.ktc2.cokaen.wouldyouin.review.persist.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EntityGettable<Long, Event> eventService;
    private final EntityGettable<Long, Member> memberService;

    @Transactional(readOnly = true)
    public ReviewResponse getById(Long reviewId) {
        Review target = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        return ReviewResponse.from(target);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getAllByMemberId(Long memberId) {
        return reviewRepository.findByMemberId(memberId).stream().map(ReviewResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getAllByEventId(Long eventId) {
        return reviewRepository.findByEventId(eventId).stream().map(ReviewResponse::from)
            .toList();
    }

    @Transactional
    public ReviewResponse create(Long eventId, ReviewCreateRequest reviewCreateRequest) {
        Review review = reviewRepository.save(reviewCreateRequest.toEntity());
        review.setMember(memberService.getByIdOrThrow(reviewCreateRequest.getMemberId()));
        review.setEvent(eventService.getByIdOrThrow(eventId));
        return ReviewResponse.from(review);
    }

    @Transactional
    public ReviewResponse update(Long reviewId, ReviewEditRequest reviewEditRequest) {
        Review target = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        target.updateFrom(reviewEditRequest);
        return ReviewResponse.from(target);
    }

    @Transactional
    public void delete(Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        reviewRepository.deleteById(reviewId);
    }
}
