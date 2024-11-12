package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdvertisementImageService extends ImageService<AdvertisementImage> {

    @Value("${image.upload.ad.child-path}")
    private String childPath;
    private final ImageStorageService imageStorageService;
    private final AdvertisementImageRepository adImageRepository;

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
    protected AdvertisementImage toEntity(ImageRequest imageRequest) {
        return AdvertisementImage.builder()
            .name(imageRequest.getName())
            .size(imageRequest.getSize())
            .extension(imageRequest.getExtension())
            .build();
    }

    // TODO : ad image 삭제 인가
    @Override
    protected void validateMemberId(MemberIdentifier identifier, AdvertisementImage image) {
        if (!identifier.type().equals(MemberType.admin)) {
            throw new UnauthorizedException("광고 이미지에 접근할 권한이 없습니다.");
        }
    }

    @Transactional
    public AdvertisementImage saveImage(MultipartFile image) {
        return adImageRepository.save(toEntity(imageStorageService.saveToDirectory(image, getChildPath())));
    }

    @Transactional
    public void setAdvertisement(AdvertisementImage image, Advertisement ad) {
        image.setAdvertisement(ad);
    }
}