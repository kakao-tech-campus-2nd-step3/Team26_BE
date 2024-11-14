package org.ktc2.cokaen.wouldyouin.review.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ReviewCreateRequest {

    @NotNull(message = "이벤트 ID는 필수입니다.")
    private Long eventId;

    @NotBlank(message = "별점은 필수입니다.")
    @Min(value = 0, message = "별점은 0점 이상입니다. ")
    @Max(value = 5, message = "별점은 5점 이하입니다. ")
    private int score;

    @NotBlank(message = "내용은 필수입니다. ")
    @Size(min = 5, max = 50, message = "내용은 5자 이상 50자 이하입니다.")
    private String content;

    public Review toEntity(Member member, Event event) {
        return Review.builder()
            .score(this.score)
            .content(this.content)
            .build();
    }
}
