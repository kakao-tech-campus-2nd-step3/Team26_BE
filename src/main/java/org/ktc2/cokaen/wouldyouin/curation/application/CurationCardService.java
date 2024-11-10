package org.ktc2.cokaen.wouldyouin.curation.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityParamIsNullException;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurationCardService {

    private final CurationCardRepository curationCardRepository;
    private final CurationImageService curationImageService;

    @Transactional(readOnly = true)
    public CurationCardResponse getById(Long id) {
        return CurationCardResponse.from(
            curationCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CurationCard"))
        );
    }

    @Transactional(readOnly = true)
    public List<CurationCardResponse> getByCurationId(Curation curation) {
        Optional.ofNullable(curation).orElseThrow(() -> new EntityParamIsNullException("Curation"));
        return curationCardRepository.findAllByCurationOrderByIdAsc(curation).stream()
            .map(CurationCardResponse::from).toList();
    }

    @Transactional
    public void setCuration(CurationCard curationCard, Curation curation) {
        Optional.ofNullable(curationCard).orElseThrow(() -> new EntityParamIsNullException("CurationCard"));
        Optional.ofNullable(curation).orElseThrow(() -> new EntityParamIsNullException("Curation"));
        curationCard.setCuration(curation);
    }

    @Transactional
    public CurationCard create(CurationCardRequest request) {
        List<CurationImage> images = request.getImageIds().stream()
            .map(curationImageService::getById)
            .toList();
        CurationCard curationCard = curationCardRepository.save(request.toEntity(images));
        images.forEach(image -> curationImageService.setCuration(image, curationCard));
        return curationCard;
    }

    @Transactional
    public void delete(Long id) {
        CurationCard target = curationCardRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("CurationCard"));
        target.getCurationImages().forEach(image -> curationImageService.deleteAndDelete(image.getId()));
        curationCardRepository.deleteById(id);
    }
}