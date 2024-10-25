package org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewEventResponse {

    private Long id;
    private String title;
    private String mainImage;
}
