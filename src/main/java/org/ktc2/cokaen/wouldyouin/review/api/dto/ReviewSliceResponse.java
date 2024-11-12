package org.ktc2.cokaen.wouldyouin.review.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;
import org.springframework.data.domain.Slice;

@Getter
@Builder
public class ReviewSliceResponse {

    private List<ReviewResponse> reviews;
    private SliceInfo sliceInfo;

    public static ReviewSliceResponse from(Slice<Review> reviews, int size, Long lastId) {
        return ReviewSliceResponse.builder()
            .reviews(reviews.stream()
                .map(ReviewResponse::from).toList())
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}
