package org.ktc2.cokaen.wouldyouin.Image.application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityParamIsNullException;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

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
        Optional.ofNullable(image).orElseThrow(() -> new EntityParamIsNullException(getImageDomain().name() + " image"));
        Optional.ofNullable(member).orElseThrow(() -> new EntityParamIsNullException("baseMember"));
        image.setBaseMember(member);
    }

    // TODO: imageUrl을 MemberImage로 변환하는 로직 추가 필요
    // Todo: extension과 size 불러오기
    public MemberImage convert(String imageUrl) {
        var request = ImageRequest.of(imageStorage.save(imageUrl, subPath), 123123L, "jpg");
        return memberImageRepository.save(toEntity(request));
    }
}