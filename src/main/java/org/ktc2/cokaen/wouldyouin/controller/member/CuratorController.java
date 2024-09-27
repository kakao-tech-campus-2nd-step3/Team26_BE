package org.ktc2.cokaen.wouldyouin.controller.member;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.CuratorEditRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.domain.MemberType;
import org.ktc2.cokaen.wouldyouin.global.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.global.annotation.Authorize;
import org.ktc2.cokaen.wouldyouin.global.util.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.service.member.CuratorService;
import org.springframework.http.HttpStatus;
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

    @PutMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> updateCurator(@Authorize(MemberType.curator) MemberIdentifier identifier, @RequestBody CuratorEditRequest request) {
        return ResponseEntity.ok(new ApiResponseBody<>(true, curatorService.updateCurator(identifier.id(), request)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<MemberResponse>> createCurator(@Authorize(MemberType.normal) MemberIdentifier identifier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseBody<>(true, curatorService.createCurator(identifier.id())));
    }
}
