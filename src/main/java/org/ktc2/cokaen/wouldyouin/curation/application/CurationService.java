package org.ktc2.cokaen.wouldyouin.curation.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
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
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
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
    private final CurationImageService curationImageService;

    @Transactional(readOnly = true)
    public CurationResponse getById(Long curationId) {
        Curation curation = getByIdOrThrow(curationId);
        return CurationResponse.from(curation, getCurationCardResponses(curation));
    }

    @Transactional(readOnly = true)
    public CurationSliceResponse getAllByAreaOrderByCreatedDateDesc(Area area, Pageable pageable, Long oldLastId) {
        Slice<Curation> curations = curationRepository.findAllByAreaOrderByCreatedDateDesc(area, oldLastId, pageable);
        Long newLastId = getLastId(curations, oldLastId);
        return CurationSliceResponse.from(getCurationResponses(curations), curations.getSize(), newLastId);
    }

    @Transactional(readOnly = true)
    public CurationSliceResponse getAllByCuratorIdOrderByCreatedDateDesc(Long curatorId, Pageable pageable, Long lastId) {
        Slice<Curation> curations = curationRepository.findAllByCuratorOrderByCreatedDateDesc(
            curatorService.getByIdOrThrow(curatorId), lastId, pageable);
        Long newLastId = getLastId(curations, lastId);
        return CurationSliceResponse.from(getCurationResponses(curations), curations.getSize(), newLastId);
    }

    @Transactional
    public CurationResponse create(MemberIdentifier identifier, CurationCreateRequest curationCreateRequest) {
        Curator curator = curatorService.getByIdOrThrow(identifier.id());
        List<CurationCard> curationCards = curationCreateRequest.getCurationCards().stream()
            .map(curationCardService::create)
            .toList();
        List<Event> events = curationCreateRequest.getEventIds().stream()
            .map(eventService::getByIdOrThrow)
            .toList();
        Curation curation = curationRepository.save(
            curationCreateRequest.toEntity(curator, curationCards, events, getThumbnailUrl(curationCards)));
        curationCards.forEach(curationCard -> curationCardService.setCuration(curationCard, curation));
        return CurationResponse.from(curation, getCurationCardResponses(curation));
    }

    @Transactional
    public CurationResponse update(MemberIdentifier identifier, Long curationId, CurationEditRequest curationEditRequest) {
        Curation curation = getByIdOrThrow(curationId);
        validateCuratorId(identifier, curation);
        List<CurationCard> curationCards = curationEditRequest.getCurationCards().stream()
            .map(curationCardService::create).toList();
        List<Event> events = curationEditRequest.getEventIds().stream()
            .map(eventService::getByIdOrThrow).toList();
        curation.getCurationCards().forEach(card -> curationCardService.delete(identifier, card.getId()));
        curation.updateFrom(curationEditRequest, curationCards, events, getThumbnailUrl(curationCards));
        curationCards.forEach(curationCard -> curationCardService.setCuration(curationCard, curation));
        return CurationResponse.from(curation, getCurationCardResponses(curation));
    }

    @Transactional
    public void delete(MemberIdentifier identifier, Long curationId) {
        Curation curation = getByIdOrThrow(curationId);
        validateCuratorId(identifier, curation);
        curation.getCurationCards()
            .forEach(curationCard -> curationCardService.delete(identifier, curationCard.getId()));
        curationRepository.deleteById(curationId);
    }

    private Long getLastId(Slice<Curation> curations, Long oldLastId) {
        if (curations.hasContent()) {
            return curations.getContent().getLast().getId();
        }
        return oldLastId;
    }

    private void validateCuratorId(MemberIdentifier identifier, Curation curation) {
        if (!identifier.type().equals(MemberType.admin) && !identifier.id().equals(curation.getCurator().getId())) {
            throw new UnauthorizedException("큐레이션에 접근할 권한이 없습니다.");
        }
    }

    private Curation getByIdOrThrow(Long id) throws EntityNotFoundException {
        return curationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당하는 큐레이션을 찾을 수 없습니다."));
    }

    private List<CurationResponse> getCurationResponses(Slice<Curation> curations) {
        return curations.getContent().stream()
            .map(curation -> CurationResponse.from(curation, getCurationCardResponses(curation)))
            .toList();
    }

    private List<CurationCardResponse> getCurationCardResponses(Curation curation) {
        return curation.getCurationCards().stream()
            .map(curationCard -> CurationCardResponse.from(
                curationCard, curationCard.getCurationImages().stream()
                    .map(curationImageService::getImageUrl).toList()))
            .toList();
    }

    private String getThumbnailUrl(List<CurationCard> curationCards) {
        return Optional.ofNullable(curationCards)
            .map(List::getFirst)
            .map(CurationCard::getCurationImages)
            .map(List::getFirst)
            .map(CurationImage::getName)
            .map(curationImageService::createThumbnail)
            .orElse("");
    }
}