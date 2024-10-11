package org.ktc2.cokaen.wouldyouin.advertisement.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.advertisement.application.AdvertisementService;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/ads")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<List<AdvertisementResponse>>> getActiveAdvertisement() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, advertisementService.getAllActiveAdvertisements()));
    }

    @GetMapping("/{adId}")
    public ResponseEntity<ApiResponseBody<AdvertisementResponse>> getAdvertisementByAdId(
        @PathVariable Long adId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, advertisementService.getAdvertisementByAdId(adId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<AdvertisementResponse>> createAdvertisement(
        @RequestBody AdvertisementRequest advertisementRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponseBody<>(true, advertisementService.create(advertisementRequest)));
    }

    @PutMapping("/{adId}")
    public ResponseEntity<ApiResponseBody<AdvertisementResponse>> updateAdvertisement(
        @PathVariable Long adId, @RequestBody AdvertisementRequest advertisementRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true,
                advertisementService.update(adId, advertisementRequest)));
    }

    @DeleteMapping("/{adId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteAdvertisement(@PathVariable Long adId) {
        advertisementService.delete(adId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new ApiResponseBody<>(true, null));
    }


}
