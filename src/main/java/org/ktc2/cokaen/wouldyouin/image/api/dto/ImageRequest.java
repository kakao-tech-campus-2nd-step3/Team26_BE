package org.ktc2.cokaen.wouldyouin.image.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ImageRequest {

    private String name;
    private Long size;
    private String extension;

    public static ImageRequest of(String name, Long size, String extension) {
        return ImageRequest.builder()
            .name(name)
            .size(size)
            .extension(extension)
            .build();
    }
}