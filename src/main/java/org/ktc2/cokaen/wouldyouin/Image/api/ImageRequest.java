package org.ktc2.cokaen.wouldyouin.Image.api;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;

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

    public <T extends Image> T toEntity(Class<T> imageClass) throws Exception {
        T image = imageClass.getDeclaredConstructor().newInstance();
        image.setUrl(url);
        image.setSize(size);
        return image;
    }
}