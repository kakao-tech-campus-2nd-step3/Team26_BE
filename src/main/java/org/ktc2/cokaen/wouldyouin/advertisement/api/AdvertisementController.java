package org.ktc2.cokaen.wouldyouin.advertisement.api;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementRequest;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementResponse;
import org.ktc2.cokaen.wouldyouin.advertisement.application.AdvertisementService;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ads")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<List<AdvertisementResponse>>> getActiveAdvertisements() {
        return ApiResponse.ok(advertisementService.getAllActiveAdvertisements());
    }

    @GetMapping("/{adId}")
    public ResponseEntity<ApiResponseBody<AdvertisementResponse>> getAdvertisementByAdId(
        @PathVariable Long adId) {
        return ApiResponse.ok(advertisementService.getAdvertisementByAdId(adId));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseBody<AdvertisementResponse>> createAdvertisement(
        @Valid @RequestPart AdvertisementRequest advertisementRequest,
        @RequestPart(required = false) MultipartFile image,
        @Authorize(MemberType.admin) MemberIdentifier memberIdentifier) {
        return ApiResponse.created(advertisementService.create(advertisementRequest, image));
    }

    @PutMapping(path = "/{adId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseBody<AdvertisementResponse>> updateAdvertisement(
        @PathVariable Long adId,
        @Valid @RequestPart AdvertisementRequest advertisementRequest,
        @RequestPart(required = false) MultipartFile image,
        @Authorize(MemberType.admin) MemberIdentifier memberIdentifier) {
        return ApiResponse.ok(advertisementService.update(adId, advertisementRequest, image));
    }

    @DeleteMapping("/{adId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteAdvertisement(
        @PathVariable Long adId,
        @Authorize(MemberType.admin) MemberIdentifier memberIdentifier) {
        advertisementService.delete(adId);
        return ApiResponse.noContent();
    }
}
