package org.ktc2.cokaen.wouldyouin.Image.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageRequest {

    private String url;
    private Long size;
    private String extension;

    public static ImageRequest of(String url, Long size, String extension) {
        return ImageRequest.builder()
            .url(url)
            .size(size)
            .extension(extension)
            .build();
    }
}