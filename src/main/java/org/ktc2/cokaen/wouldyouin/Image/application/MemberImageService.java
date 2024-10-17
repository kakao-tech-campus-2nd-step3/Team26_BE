package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberImageService extends ImageService<MemberImage> {

    private final MemberImageRepository memberImageRepository;

    @Override
    public ImageDomain getImageType() {
        return ImageDomain.MEMBER;
    }

    @Override
    public ImageRepository<MemberImage> getImageRepository() {
        return memberImageRepository;
    }

    @Override
    protected MemberImage toEntity(String path, Long size, String extension) {
        return MemberImage.builder()
            .name(path)
            .size(size)
            .extension(extension)
            .build();
    }
}