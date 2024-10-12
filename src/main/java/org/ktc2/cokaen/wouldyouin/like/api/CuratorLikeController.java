package org.ktc2.cokaen.wouldyouin.like.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.like.application.CuratorLikeService;
import org.ktc2.cokaen.wouldyouin.like.application.dto.CuratorLikeResponse;
import org.ktc2.cokaen.wouldyouin.like.application.dto.LikeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/likes/curators")
public class CuratorLikeController {

    private final CuratorLikeService curatorLikeService;

    //TODO: getCuratorLikesByMemberId() 작성

    @PostMapping("/{curatorId}")
    public ResponseEntity<ApiResponseBody<CuratorLikeResponse>> createCuratorLike(
        @PathVariable Long curatorId,
        @RequestBody LikeRequest likeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponseBody<>(true, curatorLikeService.create(curatorId, likeRequest)));
    }

    @DeleteMapping("/{curatorLikeId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteCuratorLike(
        @PathVariable Long curatorLikeId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, null));
    }
}
