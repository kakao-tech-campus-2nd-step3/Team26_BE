package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberImageService extends ImageService<MemberImage>
    implements EntityGettable<List<Long>, List<MemberImage>>,
    ImageUrlToMemberImageListConverter {

    private final MemberImageRepository memberImageRepository;

    @Value("${image.upload.member.sub-path}")
    private String subPath;

    @Override
    public ImageRepository<MemberImage> getImageRepository() {
        return memberImageRepository;
    }

    @Override
    protected String getSubPath() {
        return subPath;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.MEMBER;
    }

    @Override
    protected MemberImage mapToEntityFrom(ImageRequest imageRequest) {
        return MemberImage.builder()
            .name(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .build();
    }

    @Override
    public List<MemberImage> getByIdOrThrow(List<Long> ids) throws RuntimeException {
        return ids.stream().map(id -> memberImageRepository.findById(id).orElseThrow(RuntimeException::new)).toList();
    }

    @Override
    // TODO: imageUrl을 MemberImage로 변환하는 로직 추가 필요
    public List<MemberImage> convert(String imageUrl) {
        return null;
    }
}