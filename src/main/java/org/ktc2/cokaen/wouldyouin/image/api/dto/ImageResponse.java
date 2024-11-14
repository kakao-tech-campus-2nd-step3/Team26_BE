package org.ktc2.cokaen.wouldyouin.image.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.image.persist.Image;

@Getter
@Builder
@EqualsAndHashCode
@ToString
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