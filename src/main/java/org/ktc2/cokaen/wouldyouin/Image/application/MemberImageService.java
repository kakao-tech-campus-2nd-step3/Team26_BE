package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
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
            .url(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .extension(imageRequest.getExtension())
            .build();
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