package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityParamIsNullException;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdvertisementImageService extends ImageService<AdvertisementImage> {

    private final ImageStorage imageStorage;
    private final AdvertisementImageRepository adImageRepository;
    @Value("${image.upload.ad.sub-path}")
    private String subPath;

    @Override
    protected ImageRepository<AdvertisementImage> getImageRepository() {
        return adImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.ADVERTISEMENT;
    }

    @Override
    protected String getSubPath() {
        return subPath;
    }

    @Override
    protected AdvertisementImage toEntity(ImageRequest imageRequest) {
        return AdvertisementImage.builder()
            .name(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .build();
    }

    @Transactional
    public AdvertisementImage saveAndCreateImage(MultipartFile image) {
        String path = imageStorage.save(image, getSubPath());
        return adImageRepository.save(toEntity(ImageRequest.of(path, image.getSize(), ImageStorage.getExtension(image))));
    }

    @Transactional
    public void setAd(AdvertisementImage image, Advertisement ad) {
        Optional.ofNullable(image).orElseThrow(() -> new EntityParamIsNullException(getImageDomain().name() + " image"));
        Optional.ofNullable(ad).orElseThrow(() -> new EntityParamIsNullException("advertisement"));
        image.setAdvertisement(ad);
    }
}