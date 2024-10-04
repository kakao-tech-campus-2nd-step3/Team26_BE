package org.ktc2.cokaen.wouldyouin.like.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.like.application.HostLikeService;
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
@RequestMapping("/api/likes/hosts")
public class HostLikeController {

    private HostLikeService hostLikeService;

    //TODO: getHostLikesByMemberId() 작성

    @PostMapping("/{hostId}")
    public ResponseEntity<ApiResponseBody<HostLikeResponse>> createHostLike(
        @PathVariable Long hostId, @RequestBody LikeRequest likeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponseBody<>(true, hostLikeService.create(hostId, likeRequest)));
    }

    @DeleteMapping("/{hostLikeId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteHostLike(
        @PathVariable("hostLikeId") Long hostLikeId) {
        hostLikeService.delete(hostLikeId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBody<>(true, null));
    }

}
