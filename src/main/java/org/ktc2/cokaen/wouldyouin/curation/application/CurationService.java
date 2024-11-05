package org.ktc2.cokaen.wouldyouin.curation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationRepository;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.application.CuratorService;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurationService {

    private final CurationRepository curationRepository;
    private final CuratorService curatorService;
    private final EventService eventService;
    private final CurationCardService curationCardService;

    @Transactional(readOnly = true)
    public Curation getByIdOrThrow(Long id) throws EntityNotFoundException {
        return curationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Curation"));
    }

    @Transactional(readOnly = true)
    public CurationResponse getById(Long curationId) {
        return CurationResponse.from(getByIdOrThrow(curationId));
    }

    @Transactional(readOnly = true)
    public CurationSliceResponse getAllByAreaOrderByCreatedDateDesc(Area area, Pageable pageable, Long lastId) {
        return getCurationSliceResponse(
            curationRepository.findAllByAreaOrderByCreatedDateDesc(area, lastId, pageable), lastId);
    }

    @Transactional(readOnly = true)
    public CurationSliceResponse getAllByCuratorIdOrderByCreatedDateDesc(Long curatorId, Pageable pageable, Long lastId) {
        return getCurationSliceResponse(
            curationRepository.findAllByCuratorOrderByCreatedDateDesc(
                curatorService.getByIdOrThrow(curatorId), lastId, pageable), lastId);
    }

    private CurationSliceResponse getCurationSliceResponse(Slice<Curation> curationSlice, Long lastId) {
        List<CurationResponse> curations = curationSlice.stream().map(CurationResponse::from).toList();
        if (!curationSlice.hasContent()) {
            Long id = curationSlice.getContent().getLast().getId();
            return CurationSliceResponse.of(curations, curationSlice.getSize(), id);
        }
        return CurationSliceResponse.of(curations, curationSlice.getSize(), lastId);
    }

    @Transactional
    public CurationResponse create(Long curatorId, CurationCreateRequest curationCreateRequest) {
        Curator curator = curatorService.getByIdOrThrow(curatorId);
        List<CurationCard> curationCards = curationCreateRequest.getCurationCards().stream()
            .map(curationCardService::create)
            .toList();
        List<Event> events = curationCreateRequest.getEventIds().stream()
            .map(eventService::getByIdOrThrow)
            .toList();
        Curation curation = curationRepository.save(curationCreateRequest.toEntity(curator, curationCards, events));
        curationCards.forEach(curationCard -> curationCardService.setCuration(curationCard, curation));
        return CurationResponse.from(curation);
    }

    // Todo: 큐레이터 id 검증 로직 분리
    @Transactional
    public CurationResponse update(Long curatorId, Long curationId, CurationEditRequest curationEditRequest) {
        Curation curation = getByIdOrThrow(curationId);
        if (curatorId != curation.getCurator().getId()) {
            throw new UnauthorizedException("Curator");
        }
        List<CurationCard> curationCards = curationEditRequest.getCurationCards().stream()
            .map(curationCardService::create)
            .toList();
        List<Event> events = curationEditRequest.getEventIds().stream()
            .map(eventService::getByIdOrThrow)
            .toList();
        curation.getCurationCards().forEach(card -> curationCardService.delete(card.getId()));
        curation.updateFrom(curationEditRequest, curationCards, events);
        curationCards.forEach(curationCard -> curationCardService.setCuration(curationCard, curation));
        return CurationResponse.from(curation);
    }

    // Todo: 큐레이터 id 검증 로직 분리
    @Transactional
    public void delete(Long curatorId, Long curationId) {
        Curation curation = getByIdOrThrow(curationId);
        if (curatorId != curation.getCurator().getId()) {
            throw new UnauthorizedException("Curator");
        }
        curation.getCurationCards()
            .forEach(curationCard -> curationCardService.delete(curationCard.getId()));
        curationRepository.deleteById(curationId);
    }
}