package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityParamIsNullException;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberImageService extends ImageService<MemberImage>  {

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
            .name(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .build();
    }

    @Transactional
    public void setBaseMember(MemberImage image, BaseMember member) {
        Optional.ofNullable(image).orElseThrow(() -> new EntityParamIsNullException(getImageDomain().name() + " image"));
        Optional.ofNullable(member).orElseThrow(() -> new EntityParamIsNullException("baseMember"));
        image.setBaseMember(member);
    }

    // TODO: imageUrl을 MemberImage로 변환하는 로직 추가 필요
    public MemberImage convert(String imageUrl) {
        return MemberImage.builder()
            .name("http://example.com/images/MockMemberImageUrl")
            .size(10L)
            .extension(".jpeg")
            .build();
    }
}