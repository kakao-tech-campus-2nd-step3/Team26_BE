package org.ktc2.cokaen.wouldyouin.controller.member;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.global.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.global.annotation.Authorize;
import org.ktc2.cokaen.wouldyouin.service.MemberService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("{memberId}")
    public ApiResponseBody<?> findMember(@PathVariable("memberId") UUID memberId) {
        return new ApiResponseBody<>(true, memberService.getById(memberId));
    }

    @PostMapping
    public ApiResponseBody<?> createMember(@RequestBody MemberCreateRequest createRequest) {
        return new ApiResponseBody<>(true, memberService.createMember(createRequest));
    }

    @PutMapping
    public ApiResponseBody<?> modifyMember(@Authorize UUID memberId, @RequestBody MemberEditRequest editRequest) {
        return new ApiResponseBody<>(true, memberService.modifyMember(memberId, editRequest));
    }

    @DeleteMapping
    public void deleteMember(@Authorize UUID memberId) {
        memberService.deleteMemberById(memberId);
    }
}
