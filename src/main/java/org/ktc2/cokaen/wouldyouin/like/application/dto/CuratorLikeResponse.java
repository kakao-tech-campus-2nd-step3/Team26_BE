package org.ktc2.cokaen.wouldyouin.like.application.dto;

import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLike;

@Builder
public class CuratorLikeResponse {

    private Long id;
    private Long curatorId;
    private Long memberId;

    public static CuratorLikeResponse from(CuratorLike curatorLike) {
        return CuratorLikeResponse.builder()
            .id(curatorLike.getId())
            .curatorId(curatorLike.getCuratorId())
            .memberId(curatorLike.getMemberId())
            .build();
    }

}
