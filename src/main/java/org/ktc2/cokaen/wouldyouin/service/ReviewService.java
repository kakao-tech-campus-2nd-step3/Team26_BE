package org.ktc2.cokaen.wouldyouin.service;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.controller.review.ReviewRequest;
import org.ktc2.cokaen.wouldyouin.controller.review.ReviewResponse;
import org.ktc2.cokaen.wouldyouin.domain.Review;
import org.ktc2.cokaen.wouldyouin.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse getById(Long reviewId) {
        Review target = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        return ReviewResponse.from(target);
    }

    public List<ReviewResponse> getAllByMemberId(Long memberId) {
        return reviewRepository.findByMemberId(memberId).stream().map(ReviewResponse::from)
            .toList();
    }

    public List<ReviewResponse> getAllByEventId(Long eventId) {
        return reviewRepository.findByEventId(eventId).stream().map(ReviewResponse::from)
            .toList();
    }

    public ReviewResponse create(Long eventId, ReviewRequest reviewRequest) {
        return ReviewResponse.from(reviewRepository.save(reviewRequest.toEntity(eventId)));
    }

    public ReviewResponse update(Long reviewId, ReviewRequest reviewRequest) {
        Review target = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        target.setFrom(reviewRequest);
        return ReviewResponse.from(target);
    }

    public void delete(Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        reviewRepository.deleteById(reviewId);
    }
}
