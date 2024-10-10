package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.springframework.stereotype.Component;

@Component
public class ImageServiceFactory {

    private final ConcurrentHashMap<ImageDomain, ImageService> imageTypeToImageServiceMap;

    public ImageServiceFactory(List<ImageService> imageServices) {
        imageTypeToImageServiceMap = (ConcurrentHashMap<ImageDomain, ImageService>) imageServices.stream()
            .collect(Collectors.toConcurrentMap(
                ImageService::getImageType,
                imageService -> imageService
            ));
    }

    public ImageService getImageServiceByImageType(ImageDomain imageDomain) {
        return imageTypeToImageServiceMap.get(imageDomain);
    }
}