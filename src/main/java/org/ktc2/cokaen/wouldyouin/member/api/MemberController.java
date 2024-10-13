package org.ktc2.cokaen.wouldyouin.member.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.create.HostCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit.MemberEditRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.ResponseEntity;
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

    private final BaseMemberService baseMemberService;
    private final MemberService memberService;
    private final HostService hostService;

    // 테스트: 사용자 생성
    @PostMapping("/test-create/member")
    public ResponseEntity<ApiResponseBody<MemberResponse>> testCreateMember(@Valid @RequestBody MemberCreateRequest request) {
        return ApiResponse.created(memberService.createMember(request));
    }

    // 테스트: 호스트 생성
    @PostMapping("/test-create/host")
    public ResponseEntity<ApiResponseBody<MemberResponse>> testCreateHost(@RequestBody HostCreateRequest hostCreateRequest) {
        return ApiResponse.created(hostService.createHost(hostCreateRequest));
    }

    // 테스트: 사용자 삭제
    @DeleteMapping("/test-delete/{memberId}")
    public void testDeleteMember(@PathVariable("memberId") Long id) {
        // TODO: 204 NO CONTENT 반환하게 수정필요
        baseMemberService.deleteById(id);
    }

    // 사용자 수정
    @PutMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> updateMember(@Authorize(MemberType.normal) MemberIdentifier identifier, @RequestBody MemberEditRequest editRequest) {
        return ApiResponse.ok(memberService.updateMember(identifier.id(), editRequest));
    }

    // 사용자 조회
    @GetMapping("{memberId}")
    public ResponseEntity<ApiResponseBody<MemberResponse>> findMember(@PathVariable("memberId") Long memberId) {
        return ApiResponse.ok(baseMemberService.findById(memberId));
    }

    // 사용자 삭제
    @DeleteMapping
    public void deleteMember(@Authorize({MemberType.normal, MemberType.host, MemberType.curator}) MemberIdentifier identifier) {
        // TODO: 204 NO CONTENT 반환하게 수정필요
        baseMemberService.deleteById(identifier.id());
    }
}
