package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberImageService extends ImageService<MemberImage> {

    private final MemberImageRepository memberImageRepository;
    private final BaseMemberRepository baseMemberRepository;

    @Value("${image.upload.member.sub-path}")
    private String subPath;

    @Override
    public ImageRepository<MemberImage> getImageRepository() {
        return memberImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.MEMBER;
    }

    @Override
    protected String getSubPath() {
        return subPath;
    }

    @Override
    protected MemberImage toEntity(ImageRequest imageRequest) {
        return MemberImage.builder()
            .url(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .build();
    }

    @Transactional
    public void setBaseMember(MemberImage image, BaseMember member) {
        image.setBaseMember(member);
    }

    public MemberImage convert(String imageUrl) {
        return memberImageRepository.save(toEntity(imageStorage.save(imageUrl, subPath)));
    }
}