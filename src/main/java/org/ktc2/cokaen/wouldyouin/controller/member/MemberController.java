package org.ktc2.cokaen.wouldyouin.controller.member;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberEditRequest;
import org.ktc2.cokaen.wouldyouin.domain.MemberType;
import org.ktc2.cokaen.wouldyouin.global.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.global.annotation.Authorize;
import org.ktc2.cokaen.wouldyouin.service.member.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("{memberId}")
    public ApiResponseBody<?> findMember(@PathVariable("memberId") Long memberId) {
        return new ApiResponseBody<>(true, memberService.getById(memberId));
    }

    @PostMapping
    public ApiResponseBody<?> createMember(@RequestBody MemberCreateRequest createRequest) {
        return new ApiResponseBody<>(true, memberService.createMember(createRequest));
    }

    @PutMapping
    public ApiResponseBody<?> modifyMember(@Authorize(MemberType.normal) Long memberId, @RequestBody MemberEditRequest editRequest) {
        return new ApiResponseBody<>(true, memberService.modifyMember(memberId, editRequest));
    }

    @DeleteMapping
    public void deleteMember(@Authorize(MemberType.normal) Long memberId) {
        memberService.deleteMemberById(memberId);
    }
}
