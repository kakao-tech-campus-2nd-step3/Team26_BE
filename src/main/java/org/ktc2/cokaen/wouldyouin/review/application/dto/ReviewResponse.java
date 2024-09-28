package org.ktc2.cokaen.wouldyouin.review.application.dto;

import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;

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
