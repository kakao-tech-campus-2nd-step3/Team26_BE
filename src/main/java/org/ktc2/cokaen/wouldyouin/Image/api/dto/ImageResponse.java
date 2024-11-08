package org.ktc2.cokaen.wouldyouin.Image.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;

@Getter
@Builder
public class ImageResponse {

    private Long id;
    private String url;
    private LocalDateTime createdDate;
    private Long size;

    public static ImageResponse from(Image image, String domainName) {
        System.out.println("도메인" + domainName);
        return ImageResponse.builder()
            .id(image.getId())
            .url(domainName + image.getUrl())
            .size(image.getSize())
            .createdDate(image.getCreatedDate())
            .build();
    }
}