package org.ktc2.cokaen.wouldyouin.like.api;

import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLike;

@Builder
public class HostLikeResponse {

    private Long id;
    private Long hostId;
    private Long memberId;

    public static HostLikeResponse from(HostLike hostLike) {
        return HostLikeResponse.builder()
            .id(hostLike.getId())
            .hostId(hostLike.getHostId())
            .memberId(hostLike.getMemberId())
            .build();
    }
}
