package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberImageService extends ImageService<MemberImage> implements ImageUrlToMemberImageListConverter {

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

    public List<MemberImage> getByIdOrThrow(List<Long> ids) throws RuntimeException {
        return ids.stream().map(id -> memberImageRepository.findById(id).orElseThrow(RuntimeException::new)).toList();
    }

    // TODO: imageUrl을 MemberImage로 변환하는 로직 추가 필요
    @Override
    public List<MemberImage> convert(String imageUrl) {
        return List.of(MemberImage.builder()
            .name("http://example.com/images/MockMemberImageUrl")
            .size(10L)
            .extension(".jpeg")
            .build());
    }
}