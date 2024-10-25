package org.ktc2.cokaen.wouldyouin.curation.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationService;
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
    public ApiResponseBody<List<CurationResponse>> getCurationByArea(@RequestParam Area area) {
        return new ApiResponseBody<>(true, curationService.getAllByArea(area));
    }

    @GetMapping("/{curationId}")
    public ApiResponseBody<CurationResponse> getCurationByCurationId(
        @PathVariable("curationId") Long curationId) {
        return new ApiResponseBody<>(true, curationService.getById(curationId));
    }

    @PostMapping
    public ApiResponseBody<CurationResponse> createCuration(
        @RequestBody CurationCreateRequest curationCreateRequest) {
        return new ApiResponseBody<>(true, curationService.create(curationCreateRequest));
    }

    @PutMapping("/{curationId}")
    public ApiResponseBody<CurationResponse> updateCuration(@PathVariable Long curationId,
        @RequestBody CurationEditRequest curationEditRequest) {
        return new ApiResponseBody<>(true, curationService.update(curationId, curationEditRequest));

    }

    @DeleteMapping("/{curationId}")
    public ApiResponseBody<Void> deleteCuration(@PathVariable Long curationId) {
        curationService.delete(curationId);
        return new ApiResponseBody<>(true, null);
    }

}
