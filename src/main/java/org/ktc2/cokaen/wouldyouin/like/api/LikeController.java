package org.ktc2.cokaen.wouldyouin.like.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.like.application.LikeServiceFactory;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final BaseMemberService baseMemberService;

    @GetMapping
    public ResponseEntity<?> getLikes(@Authorize(MemberType.normal) MemberIdentifier identifier, @RequestParam("type") MemberType memberType) {
        return ApiResponse.ok(likeServiceFactory.getLikeService(memberType).getLikes(identifier.id()));
    }

    @PostMapping("/{targetMemberId}")
    public ResponseEntity<?> createLike(@Authorize(MemberType.normal) MemberIdentifier identifier, @PathVariable("targetMemberId") Long targetId) {
        MemberType targetMemberType = baseMemberService.getMemberType(targetId);
        return ApiResponse.created(likeServiceFactory.getLikeService(targetMemberType).create(identifier.id(), targetId, targetMemberType));
    }

    @DeleteMapping("/{targetMemberId}")
    public ResponseEntity<?> deleteLike(@Authorize(MemberType.normal) MemberIdentifier identifier, @PathVariable("targetMemberId") Long targetId) {
        MemberType targetMemberType = baseMemberService.getMemberType(targetId);
        likeServiceFactory.getLikeService(targetMemberType).delete(identifier.id(), targetId, targetMemberType);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseBody<>(true, null));
    }
}
