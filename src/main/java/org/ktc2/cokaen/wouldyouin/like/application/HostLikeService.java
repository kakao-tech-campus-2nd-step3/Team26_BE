package org.ktc2.cokaen.wouldyouin.like.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.like.api.HostLikeResponse;
import org.ktc2.cokaen.wouldyouin.like.api.LikeRequest;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HostLikeService {

    private final HostLikeRepository hostLikeRepository;

    @Transactional(readOnly = true)
    public List<HostLikeResponse> findByMemberId(Long memberId){
        return hostLikeRepository.findByMemberId(memberId).stream().map(HostLikeResponse::from).toList();
    }

    @Transactional
    public HostLikeResponse create(Long hostId, LikeRequest likeRequest) {
        return HostLikeResponse.from(
            hostLikeRepository.save(likeRequest.toHostLikeEntity(hostId, likeRequest)));
    }

    @Transactional
    public void delete(Long hostLikeId) {
        hostLikeRepository.findById(hostLikeId).orElseThrow(RuntimeException::new);
        hostLikeRepository.deleteById(hostLikeId);
    }
}
