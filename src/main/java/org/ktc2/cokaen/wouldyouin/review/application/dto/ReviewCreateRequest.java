package org.ktc2.cokaen.wouldyouin.review.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;

@Getter
@Builder
public class ReviewCreateRequest {

    private Long memberId;
    private int score;
    private String content;

    public Review toEntity() {
        return Review.builder()
            .score(this.score)
            .content(this.content)
            .build();
    }
}
