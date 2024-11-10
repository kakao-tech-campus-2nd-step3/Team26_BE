package org.ktc2.cokaen.wouldyouin.review.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.ReviewMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;

@Builder
@Getter
public class ReviewResponse {

    private Long id;
    private ReviewMemberResponse member;
    private ReviewEventResponse event;
    private int score;
    private String content;

    public static ReviewResponse from(final Review review) {
        Member member = review.getMember();
        Event eventInReview = review.getEvent();
        return ReviewResponse.builder()
            .id(review.getId())
            .member(ReviewMemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build())
            .event(ReviewEventResponse.builder()
                .id(eventInReview.getId())
                .title(eventInReview.getTitle())
//                .mainImage(eventInCuration.getMainImage())
                .build())
            .score(review.getScore())
            .content(review.getContent())
            .build();
    }

}
