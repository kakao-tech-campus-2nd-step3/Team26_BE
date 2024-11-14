package org.ktc2.cokaen.wouldyouin.member.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit.CuratorEditRequest;
import org.ktc2.cokaen.wouldyouin.member.application.CuratorService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curators")
public class CuratorController {

    private final CuratorService curatorService;

    @PostMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> createCurator(@Authorize(MemberType.normal) MemberIdentifier identifier) {
        return ApiResponse.created(curatorService.createCurator(identifier.id()));
    }

    @PutMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> updateCurator(@Authorize(MemberType.curator) MemberIdentifier identifier, @RequestBody CuratorEditRequest request) {
        return ApiResponse.ok(curatorService.updateCurator(identifier.id(), request));
    }
}
