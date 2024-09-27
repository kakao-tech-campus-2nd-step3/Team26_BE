package org.ktc2.cokaen.wouldyouin.controller.curation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.global.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.service.CurationService;
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
        @RequestBody CurationRequest curationRequest) {
        return new ApiResponseBody<>(true, curationService.create(curationRequest));
    }

    @PutMapping("/{curationId}")

    public ApiResponseBody<CurationResponse> updateCuration(@PathVariable Long curationId,
        @RequestBody CurationRequest curationRequest) {
        return new ApiResponseBody<>(true, curationService.update(curationId, curationRequest));

    }

    @DeleteMapping("/{curationId}")
    public ApiResponseBody<Void> deleteCuration(@PathVariable Long curationId) {
        curationService.delete(curationId);
        return new ApiResponseBody<>(true, null);
    }
}
