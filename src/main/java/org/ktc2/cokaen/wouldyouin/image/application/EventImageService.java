package org.ktc2.cokaen.wouldyouin.image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.image.persist.EventImageRepository;
import org.ktc2.cokaen.wouldyouin.image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventImageService extends ImageService<EventImage> {

    @Value("${image.upload.event.child-path}")
    private String childPath;
    private final EventImageRepository eventImageRepository;

    public EventImageService(ImageStorageService imageStorageService, EventImageRepository eventImageRepository) {
        this.imageStorageService = imageStorageService;
        this.eventImageRepository = eventImageRepository;
    }

    @Override
    public ImageRepository<EventImage> getImageRepository() {
        return eventImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.EVENT;
    }

    @Override
    protected String getChildPath() {
        return childPath;
    }

    @Override
    protected EventImage mapToEntityFrom(ImageRequest imageRequest) {
        return EventImage.builder()
            .name(imageRequest.getName())
            .size(imageRequest.getSize())
            .extension(imageRequest.getExtension())
            .build();
    }

    @Override
    protected void validateMemberId(MemberIdentifier identifier, EventImage image) {
        if (!identifier.type().equals(MemberType.admin) &&
            !identifier.id().equals(image.getEvent().getHost().getId())) {
            throw new UnauthorizedException("해당 이벤트 이미지에 접근할 권한이 없습니다.");
        }
    }

    @Transactional
    public void setEvent(EventImage image, Event event) {
        image.setEvent(event);
    }
}