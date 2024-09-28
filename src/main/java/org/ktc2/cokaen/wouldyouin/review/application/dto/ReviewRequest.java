package org.ktc2.cokaen.wouldyouin.review.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;

@Getter
@Builder
public class ReviewRequest {

    private Long memberId;
    private int score;
    private String content;

    public Review toEntity(Long eventId) {
        return Review.builder()
            .memberId(this.memberId)
            .eventId(eventId)
            .score(this.score)
            .content(this.content)
            .build();
    }
}
