package org.ktc2.cokaen.wouldyouin.Image.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.application.ImageServiceFactory;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
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
@RequestMapping("/api")
public class ImageController {

    private final ImageServiceFactory imageServiceFactory;

    @PostMapping("/images")
    public ResponseEntity<ApiResponseBody<List<ImageResponse>>> uploadImages(@RequestParam List<MultipartFile> images,
        @RequestParam ImageDomain imageDomain) {
        return ApiResponse.ok(imageServiceFactory.getImageServiceByImageType(imageDomain).saveAndCreateImages(images));
    }

    @GetMapping(value = "{path}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String path) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("failed to get image.");
        }
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<ApiResponseBody<Void>> deleteImage(@PathVariable Long id, @RequestParam ImageDomain imageDomain) {
        imageServiceFactory.getImageServiceByImageType(imageDomain).deleteAndDelete(id);
        return ApiResponse.noContent();
    }
}