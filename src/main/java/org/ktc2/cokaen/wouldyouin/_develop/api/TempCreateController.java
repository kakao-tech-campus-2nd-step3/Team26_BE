package org.ktc2.cokaen.wouldyouin._develop.api;

import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.TokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.create.HostCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.application.CuratorService;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TempCreateController {

    private final BaseMemberService baseMemberService;
    private final MemberService memberService;
    private final HostService hostService;
    private final CuratorService curatorService;
    private final JwtService jwtService;

    // 테스트: 사용자 생성
    @PostMapping("/create/member")
    public ResponseEntity<ApiResponseBody<Map<String, Object>>> testCreateMember(@Valid @RequestBody MemberCreateRequest request) {
        MemberResponse response = memberService.createMember(request);
        MemberIdentifier identifier = new MemberIdentifier(response.getMemberId(), response.getMemberType());
        return ApiResponse.created(Map.of("response", response, "token", createToken(identifier)));
    }

    // 테스트: 호스트 생성
    @PostMapping("/create/host")
    public ResponseEntity<ApiResponseBody<Map<String, Object>>> testCreateHost(@RequestBody HostCreateRequest request) {
        MemberResponse response = hostService.createHost(request);
        MemberIdentifier identifier = new MemberIdentifier(response.getMemberId(), response.getMemberType());
        return ApiResponse.created(Map.of("response", response, "token", createToken(identifier)));
    }

    // 테스트: 큐레이터 생성
    @PostMapping("/create/curator")
    public ResponseEntity<ApiResponseBody<Map<String, Object>>> testCreateCurator(@RequestParam("memberId") Long id) {
        MemberResponse response = curatorService.createCurator(id);
        MemberIdentifier identifier = new MemberIdentifier(response.getMemberId(), response.getMemberType());
        return ApiResponse.created(Map.of("response", response, "token", createToken(identifier)));
    }

    // 테스트: 사용자 삭제
    @DeleteMapping("/delete/{memberId}")
    public void testDeleteMember(@PathVariable("memberId") Long id) {
        // TODO: 204 NO CONTENT 반환하게 수정필요
        baseMemberService.deleteById(id);
    }

    @GetMapping("create/token")
    public ResponseEntity<ApiResponseBody<TokenResponse>> testCreateToken(
        @RequestParam("memberId") Long memberId, @RequestParam("type") MemberType memberType) {
        return ApiResponse.ok(createToken(new MemberIdentifier(memberId, memberType)));
    }

    private TokenResponse createToken(MemberIdentifier identifier) {
        return TokenResponse.from(jwtService.createAccessToken(identifier.id(), identifier.type()));
    }
}
