package org.ktc2.cokaen.wouldyouin.Image.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;

@Getter
@Builder
public class ImageRequest {

    private String url;
    private Long size;

    public static ImageRequest of(String url, Long size) {
        return ImageRequest.builder()
            .url(url)
            .size(size)
            .build();
    }
}