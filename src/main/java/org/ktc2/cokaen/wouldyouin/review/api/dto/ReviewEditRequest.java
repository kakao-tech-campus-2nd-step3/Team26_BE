package org.ktc2.cokaen.wouldyouin.review.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ReviewEditRequest {

    private Integer score;
    private String content;
}
