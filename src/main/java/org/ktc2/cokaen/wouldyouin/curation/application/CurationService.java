package org.ktc2.cokaen.wouldyouin.curation.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationRepository;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CurationService implements EntityGettable<Long, Curation> {

    private final CurationRepository curationRepository;
    private final EntityGettable<Long, Curator> curatorService;
    private final EntityGettable<Long, Event> eventService;
    private final EntityGettable<List<Long>, List<CurationImage>> curationImageService;

    @Override
    @Transactional(readOnly = true)
    public Curation getByIdOrThrow(Long id) throws RuntimeException {
        return curationRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public CurationResponse getById(Long curationId) {
        return CurationResponse.from(getByIdOrThrow(curationId));
    }

    @Transactional(readOnly = true)
    public List<CurationResponse> getAllByArea(Area area) {
        return curationRepository.findByArea(area).stream()
            .map(CurationResponse::from).toList();
    }

    @Transactional
    public CurationResponse create(CurationCreateRequest curationCreateRequest) {
        Curation curation = curationRepository.save(curationCreateRequest.toEntity());
        curation.setCurator(curatorService.getByIdOrThrow(curationCreateRequest.getCuratorId()));
        curation.setEvent(eventService.getByIdOrThrow(curationCreateRequest.getEventId()));
        curation.setImages(
            curationImageService.getByIdOrThrow(curationCreateRequest.getImageIds()));
        return CurationResponse.from(curation);
    }

    @Transactional
    public CurationResponse update(Long curationId, CurationEditRequest curationEditRequest) {
        Curation target = curationRepository.findById(curationId)
            .orElseThrow(RuntimeException::new);
        target.updateFrom(curationEditRequest);
        target.setEvent(eventService.getByIdOrThrow(curationEditRequest.getEventId()));
        target.setImages(curationImageService.getByIdOrThrow(curationEditRequest.getImageIds()));
        return CurationResponse.from(target);
    }

    @Transactional
    public void delete(Long curationId) {
        curationRepository.findById(curationId).orElseThrow(RuntimeException::new);
        curationRepository.deleteById(curationId);
    }
}
