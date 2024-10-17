package org.ktc2.cokaen.wouldyouin.curation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.application.dto.CurationRequest;
import org.ktc2.cokaen.wouldyouin.curation.application.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurationService implements EntityGettable<Long, Curation> {

    private final CurationRepository curationRepository;

    @Transactional
    public CurationResponse create(CurationRequest curationRequest) {
        return CurationResponse.from(curationRepository.save(curationRequest.toEntity()));
    }

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
    public CurationResponse update(Long curationId, CurationRequest curationRequest) {
        Curation target = curationRepository.findById(curationId)
            .orElseThrow(RuntimeException::new);
        target.setFrom(curationRequest);
        return CurationResponse.from(target);
    }

    @Transactional
    public void delete(Long curationId) {
        curationRepository.findById(curationId).orElseThrow(RuntimeException::new);
        curationRepository.deleteById(curationId);
    }
}
