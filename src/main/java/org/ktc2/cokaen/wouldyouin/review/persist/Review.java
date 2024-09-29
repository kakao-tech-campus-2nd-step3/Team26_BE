package org.ktc2.cokaen.wouldyouin.review.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.review.application.dto.ReviewRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String content;

    @Builder
    protected Review(Long memberId, Long eventId, int score, String content) {
        this.memberId = memberId;
        this.eventId = eventId;
        this.score = score;
        this.content = content;
    }

    public void setFrom(ReviewRequest reviewRequest) {
        this.memberId = reviewRequest.getMemberId();
        this.score = reviewRequest.getScore();
        this.content = reviewRequest.getContent();
    }
}
