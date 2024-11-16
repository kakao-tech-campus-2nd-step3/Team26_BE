package org.ktc2.cokaen.wouldyouin.image.api;

import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.image.application.ImageServiceFactory;
import org.ktc2.cokaen.wouldyouin.image.application.ImageStorageService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageServiceFactory imageServiceFactory;
    private final ImageStorageService imageStorageService;

    @GetMapping(value = "/{directory}/{file}", produces = {MediaType.IMAGE_PNG_VALUE,
        MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String directory,
        @PathVariable String file) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(imageStorageService.readFromDirectory(Paths.get(directory, file)));
    }

    @GetMapping(value = "/{directory}/{thumbnail}/{file}", produces = {MediaType.IMAGE_PNG_VALUE,
        MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getThumnailImage(@PathVariable String directory,
        @PathVariable String thumbnail,
        @PathVariable String file) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(imageStorageService.readFromDirectory(Paths.get(directory, thumbnail, file)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<List<ImageResponse>>> uploadImages(
        @RequestParam List<MultipartFile> images,
        @RequestParam(value = "type") ImageDomain imageDomain) {
        return ApiResponse.ok(imageServiceFactory.getImageService(imageDomain).saveImages(images));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteImage(
        @PathVariable Long imageId,
        @RequestParam(value = "type") ImageDomain imageDomain,
        @Authorize({MemberType.normal, MemberType.curator,
            MemberType.host}) MemberIdentifier identifier) {
        imageServiceFactory.getImageService(imageDomain).deleteImage(identifier, imageId);
        return ApiResponse.noContent();
    }
}