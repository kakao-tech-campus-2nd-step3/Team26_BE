package org.ktc2.cokaen.wouldyouin.controller.review;

import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.domain.Review;

@Builder
public class ReviewResponse {

    private Long id;
    private Long memberId;
    private Long eventId;
    private int score;
    private String content;

    public static ReviewResponse from(final Review review) {
        return ReviewResponse.builder()
            .id(review.getId())
            .memberId(review.getMemberId())
            .eventId(review.getEventId())
            .score(review.getScore())
            .content(review.getContent())
            .build();
    }

}
