package org.ktc2.cokaen.wouldyouin.curation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
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
    public CurationCard getByIdOrThrow(Long id) {
        return curationCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당하는 큐레이션 카드를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public CurationCardResponse getById(Long id) {
        return CurationCardResponse.from(getByIdOrThrow(id));
    }

    @Transactional
    public void setCuration(CurationCard curationCard, Curation curation) {
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
    public void delete(MemberIdentifier identifier, Long id) {
        CurationCard target = getByIdOrThrow(id);
        target.getCurationImages().forEach(image -> curationImageService.deleteImage(identifier, image.getId()));
        curationCardRepository.deleteById(id);
    }
}