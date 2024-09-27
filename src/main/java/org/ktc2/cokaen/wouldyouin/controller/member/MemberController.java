package org.ktc2.cokaen.wouldyouin.controller.member;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberEditRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.domain.MemberType;
import org.ktc2.cokaen.wouldyouin.global.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.global.annotation.Authorize;
import org.ktc2.cokaen.wouldyouin.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    //TODO: MemberSearchService 이용 필요
    @GetMapping("{memberId}")
    public ResponseEntity<ApiResponseBody<MemberResponse>> findMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(new ApiResponseBody<>(true, memberService.getById(memberId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> createMember(@RequestBody MemberCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponseBody<>(true, memberService.createMember(createRequest)));
    }

    @PutMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> updateMember(@Authorize(MemberType.normal) Long memberId, @RequestBody MemberEditRequest editRequest) {
        return ResponseEntity.ok(new ApiResponseBody<>(true, memberService.updateMember(memberId, editRequest)));
    }

    @DeleteMapping
    public void deleteMember(@Authorize(MemberType.normal) Long memberId) {
        memberService.deleteMemberById(memberId);
    }
}
