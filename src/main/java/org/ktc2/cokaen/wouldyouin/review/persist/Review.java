package org.ktc2.cokaen.wouldyouin.review.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewEditRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @NotNull
    @Min(0)
    @Column(name = "score")
    private Integer score;

    @NotNull
    @Column(name = "content")
    private String content;

    @Builder
    public Review(Member member, Event event, Integer score, String content) {
        this.member = member;
        this.event = event;
        this.score = score;
        this.content = content;
    }

    public void updateFrom(ReviewEditRequest reviewEditRequest) {
        Optional.ofNullable(reviewEditRequest.getScore()).ifPresent(this::setScore);
        Optional.ofNullable(reviewEditRequest.getContent()).ifPresent(this::setContent);
    }
}
