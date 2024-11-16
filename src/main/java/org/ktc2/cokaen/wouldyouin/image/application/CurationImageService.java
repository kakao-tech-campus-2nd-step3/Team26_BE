package org.ktc2.cokaen.wouldyouin.image.application;

import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationCardImage;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationCardImageRepository;
import org.ktc2.cokaen.wouldyouin.image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurationImageService extends ImageService<CurationCardImage> {

    private final CurationCardImageRepository curationCardImageRepository;
    @Value("${image.upload.curation.child-path}")
    private String childPath;

    public CurationImageService(ImageStorageService imageStorageService,
        CurationCardImageRepository curationCardImageRepository) {
        this.imageStorageService = imageStorageService;
        this.curationCardImageRepository = curationCardImageRepository;
    }

    @Override
    public ImageRepository<CurationCardImage> getImageRepository() {
        return curationCardImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.CURATION;
    }

    @Override
    protected String getChildPath() {
        return childPath;
    }

    @Override
    protected CurationCardImage mapToEntityFrom(ImageRequest imageRequest) {
        return CurationCardImage.builder()
            .name(imageRequest.getName())
            .size(imageRequest.getSize())
            .extension(imageRequest.getExtension())
            .build();
    }

    @Override
    protected void validateMemberId(MemberIdentifier identifier, CurationCardImage image) {
        if (!identifier.type().equals(MemberType.admin) &&
            !identifier.id().equals(image.getCurationCard().getCuration().getCurator().getId())) {
            throw new UnauthorizedException("해당 큐레이션 이미지에 접근할 권한이 없습니다.");
        }
    }

    @Transactional
    public void setCuration(CurationCardImage image, CurationCard curationCard) {
        image.setCurationCard(curationCard);
    }
}