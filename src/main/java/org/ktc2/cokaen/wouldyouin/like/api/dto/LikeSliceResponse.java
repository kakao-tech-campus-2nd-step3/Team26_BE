package org.ktc2.cokaen.wouldyouin.like.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class LikeSliceResponse {

    private List<LikeResponse> likes;
    private SliceInfo sliceInfo;

    public static LikeSliceResponse from(List<LikeResponse> likes, int size, Long lastId) {
        return LikeSliceResponse.builder()
            .likes(likes)
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}
