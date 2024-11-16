package org.ktc2.cokaen.wouldyouin.image.application;

import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.image.persist.AdvertisementImageRepository;
import org.ktc2.cokaen.wouldyouin.image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdvertisementImageService extends ImageService<AdvertisementImage> {

    private final AdvertisementImageRepository adImageRepository;
    @Value("${image.upload.ad.child-path}")
    private String childPath;

    public AdvertisementImageService(ImageStorageService imageStorageService,
        AdvertisementImageRepository adImageRepository) {
        this.imageStorageService = imageStorageService;
        this.adImageRepository = adImageRepository;
    }

    @Override
    protected ImageRepository<AdvertisementImage> getImageRepository() {
        return adImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.ADVERTISEMENT;
    }

    @Override
    protected String getChildPath() {
        return childPath;
    }

    @Override
    protected AdvertisementImage mapToEntityFrom(ImageRequest imageRequest) {
        return AdvertisementImage.builder()
            .name(imageRequest.getName())
            .size(imageRequest.getSize())
            .extension(imageRequest.getExtension())
            .build();
    }

    @Override
    protected void validateMemberId(MemberIdentifier identifier, AdvertisementImage image) {
        if (!identifier.type().equals(MemberType.admin)) {
            throw new UnauthorizedException("광고 이미지에 접근할 권한이 없습니다.");
        }
    }

    @Transactional
    public AdvertisementImage saveImage(MultipartFile image) {
        return adImageRepository.save(
            mapToEntityFrom(imageStorageService.saveToDirectory(image, getChildPath())));
    }

    @Transactional
    public void setAdvertisement(AdvertisementImage image, Advertisement ad) {
        image.setAdvertisement(ad);
    }
}