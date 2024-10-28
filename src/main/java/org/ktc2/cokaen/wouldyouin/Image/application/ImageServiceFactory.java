package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageServiceFactory {

    private final ConcurrentHashMap<ImageDomain, ImageService<? extends Image>> imageTypeToImageServiceMap;

    public ImageServiceFactory(List<ImageService<? extends Image>> imageServices) {
        imageTypeToImageServiceMap = new ConcurrentHashMap<>(imageServices.stream()
            .collect(Collectors.toConcurrentMap(ImageService::getImageDomain, imageService -> imageService)));
    }

    public ImageService<? extends Image> getImageServiceByImageType(ImageDomain imageDomain) {
        return imageTypeToImageServiceMap.get(imageDomain);
    }


}