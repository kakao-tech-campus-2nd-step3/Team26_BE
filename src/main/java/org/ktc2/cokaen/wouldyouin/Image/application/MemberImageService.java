package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberImageService extends ImageService<MemberImage> {

    @Value("${image.upload.member.child-path}")
    private String childPath;
    private final MemberImageRepository memberImageRepository;

    @Override
    public ImageRepository<MemberImage> getImageRepository() {
        return memberImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.MEMBER;
    }

    @Override
    protected String getChildPath() {
        return childPath;
    }

    @Override
    protected MemberImage toEntity(ImageRequest imageRequest) {
        return MemberImage.builder()
            .name(imageRequest.getName())
            .size(imageRequest.getSize())
            .extension(imageRequest.getExtension())
            .build();
    }

    @Override
    protected void validateMemberId(MemberIdentifier identifier, MemberImage image) {
        if (!identifier.type().equals(MemberType.admin) && !identifier.id().equals(image.getBaseMember().getId())) {
            throw new UnauthorizedException("해당 프로필 이미지에 접근할 권한이 없습니다.");
        }
    }

    @Transactional
    public void setBaseMember(MemberImage image, BaseMember member) {
        image.setBaseMember(member);
    }

    public MemberImage convert(String imageUrl) {
        ImageRequest imageRequest = imageStorageService.saveToDirectory(imageUrl, childPath);
        return memberImageRepository.save(toEntity(imageRequest));
    }
}