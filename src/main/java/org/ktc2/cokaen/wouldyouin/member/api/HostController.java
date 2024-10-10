package org.ktc2.cokaen.wouldyouin.member.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit.HostEditRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hosts")
public class HostController {

    private final HostService hostService;

    @PutMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> updateHost(@Authorize(MemberType.host) MemberIdentifier identifier, @RequestBody HostEditRequest request) {
        return ApiResponse.ok(hostService.updateHost(identifier.id(), request));
    }

}
