package org.ktc2.cokaen.wouldyouin.Image.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.ktc2.cokaen.wouldyouin._common.util.UriUtil;

@Getter
@Builder
public class ImageResponse {

    private Long id;
    private String url;
    private Long size;
    private String extension;
    private LocalDateTime createdDate;

    public static ImageResponse from(Image image, String url) {
        return ImageResponse.builder()
            .id(image.getId())
            .url(url)
            .size(image.getSize())
            .extension(image.getExtension())
            .createdDate(image.getCreatedDate())
            .build();
    }
}