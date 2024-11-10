package org.ktc2.cokaen.wouldyouin.Image.api.dto;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;

@Getter
@Builder
public class ImageResponse {

    private Long id;
    private String url;
    private Long size;
    private String extension;
    private LocalDateTime createdDate;

    public static ImageResponse from(Image image, String apiUrlHeader) {
        return ImageResponse.builder()
            .id(image.getId())
            .url(Paths.get(apiUrlHeader, image.getUrl()).toString())
            .size(image.getSize())
            .extension(image.getExtension())
            .createdDate(image.getCreatedDate())
            .build();
    }
}