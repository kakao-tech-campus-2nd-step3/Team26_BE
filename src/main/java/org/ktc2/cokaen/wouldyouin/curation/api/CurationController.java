package org.ktc2.cokaen.wouldyouin.curation.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.api.ParamDefaults;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curations")
public class CurationController {

    private final CurationService curationService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<CurationSliceResponse>> getCurationsByAreaOrderByCreatedDateDesc(
        @RequestParam(defaultValue = ParamDefaults.AREA) Area area,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId
    ) {
        return ApiResponse.ok(curationService.getAllByAreaOrderByCreatedDateDesc(
            area, PageRequest.of(page, size), lastId));
    }

    @GetMapping("/curators/{curatorId}")
    public ResponseEntity<ApiResponseBody<CurationSliceResponse>> getCurationsByCuratorIdOrderByCreatedDateDesc(
        @PathVariable("curatorId") Long curatorId,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId
    ) {
        return ApiResponse.ok(curationService.getAllByCuratorIdOrderByCreatedDateDesc(
            curatorId, PageRequest.of(page, size), lastId));
    }

    @GetMapping("/{curationId}")
    public ResponseEntity<ApiResponseBody<CurationResponse>> getCurationByCurationId(
        @PathVariable("curationId") Long curationId) {
        return ApiResponse.ok(curationService.getById(curationId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<CurationResponse>> createCuration(
        @Valid @RequestBody CurationCreateRequest curationCreateRequest,
        @Authorize(MemberType.curator) MemberIdentifier curator) {
        return ApiResponse.created(curationService.create(curator.id(), curationCreateRequest));
    }

    @PutMapping("/{curationId}")
    public ResponseEntity<ApiResponseBody<CurationResponse>> updateCuration(
        @PathVariable Long curationId,
        @Valid @RequestBody CurationEditRequest curationEditRequest,
        @Authorize(MemberType.curator) MemberIdentifier curator) {
        return ApiResponse.ok(curationService.update(curator.id(), curationId, curationEditRequest));
    }

    @DeleteMapping("/{curationId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteCuration(
        @PathVariable Long curationId,
        @Authorize(MemberType.curator) MemberIdentifier curator) {
        curationService.delete(curator.id(), curationId);
        return ApiResponse.noContent();
    }
}