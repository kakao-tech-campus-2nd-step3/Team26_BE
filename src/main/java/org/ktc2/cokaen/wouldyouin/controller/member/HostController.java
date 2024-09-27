package org.ktc2.cokaen.wouldyouin.controller.member;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.HostCreateRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.HostEditRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.domain.MemberType;
import org.ktc2.cokaen.wouldyouin.global.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.global.annotation.Authorize;
import org.ktc2.cokaen.wouldyouin.global.util.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.service.member.HostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hosts")
public class HostController {

    private final HostService hostService;

    @PostMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> createHost(@RequestBody HostCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseBody<>(true, hostService.createHost(request)));
    }

    @PutMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> updateHost(@Authorize(MemberType.host) MemberIdentifier identifier, @RequestBody HostEditRequest request) {
        return ResponseEntity.ok(new ApiResponseBody<>(true, hostService.updateHost(identifier.id(), request)));
    }

}
