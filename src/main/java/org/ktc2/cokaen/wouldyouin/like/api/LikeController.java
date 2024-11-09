package org.ktc2.cokaen.wouldyouin.like.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.config.ParamDefaults;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.like.application.LikeServiceFactory;
import org.ktc2.cokaen.wouldyouin.like.api.dto.LikeResponse;
import org.ktc2.cokaen.wouldyouin.like.api.dto.LikeToggleResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeServiceFactory likeServiceFactory;

    @GetMapping
    public ResponseEntity<ApiResponseBody<Slice<LikeResponse>>> getLikes(
        @Authorize(MemberType.normal) MemberIdentifier identifier,
        @RequestParam("type") MemberType memberType,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId
    ) {
        return ApiResponse.ok(
            likeServiceFactory.getLikeServiceFrom(memberType)
                .getLikes(identifier.id(), PageRequest.of(page, size), lastId));
    }

    @PostMapping("/{targetMemberId}")
    public ResponseEntity<ApiResponseBody<LikeToggleResponse>> createOrDeleteLike(
        @Authorize(MemberType.normal) MemberIdentifier identifier,
        @PathVariable("targetMemberId") Long targetId) {
        return ApiResponse.created(
            likeServiceFactory.getLikeServiceFrom(targetId).toggleLike(identifier.id(), targetId));
    }
}
