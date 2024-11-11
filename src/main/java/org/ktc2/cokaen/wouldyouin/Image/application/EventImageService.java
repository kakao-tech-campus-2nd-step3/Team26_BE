package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EventImageService extends ImageService<EventImage> {

    @Value("${image.upload.event.child-path}")
    private String childPath;
    private final EventImageRepository eventImageRepository;

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
    protected EventImage toEntity(ImageRequest imageRequest) {
        return EventImage.builder()
            .url(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .extension(imageRequest.getExtension())
            .build();
    }

    @Transactional
    public void setEvent(EventImage image, Event event) {
        image.setEvent(event);
    }
}