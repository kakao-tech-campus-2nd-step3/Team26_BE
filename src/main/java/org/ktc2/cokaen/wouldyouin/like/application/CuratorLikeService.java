package org.ktc2.cokaen.wouldyouin.like.application;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.like.application.dto.CuratorLikeResponse;
import org.ktc2.cokaen.wouldyouin.like.application.dto.LikeRequest;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CuratorLikeService {

    private final CuratorLikeRepository curatorLikeRepository;

    @Transactional(readOnly = true)
    public List<CuratorLikeResponse> getAllByMemberId(Long memberId) {
        return curatorLikeRepository.findByMemberId(memberId).stream()
            .map(CuratorLikeResponse::from).toList();
    }

    @Transactional
    public CuratorLikeResponse create(Long curatorId, LikeRequest likeRequest) {
        return CuratorLikeResponse.from(
            curatorLikeRepository.save(likeRequest.toCuratorLikeEntity(curatorId, likeRequest)));
    }

    @Transactional
    public void delete(Long curatorLikeId) {
        curatorLikeRepository.findById(curatorLikeId).orElseThrow(RuntimeException::new);
        curatorLikeRepository.deleteById(curatorLikeId);
    }
}
