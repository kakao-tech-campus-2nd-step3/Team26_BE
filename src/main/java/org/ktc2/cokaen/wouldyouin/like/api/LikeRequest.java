package org.ktc2.cokaen.wouldyouin.like.api;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLike;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLike;

@Getter
@Builder(toBuilder = true)
public class LikeRequest {

    private Long memberId;

    public HostLike toHostLikeEntity(Long hostId, LikeRequest likeRequest) {
        return HostLike.builder()
            .hostId(hostId)
            .memberId(likeRequest.getMemberId())
            .build();
    }

    public CuratorLike toCuratorLikeEntity(Long curatorId, LikeRequest likeRequest) {
        return CuratorLike.builder()
            .curatorId(curatorId)
            .memberId(likeRequest.getMemberId())
            .build();
    }
}
