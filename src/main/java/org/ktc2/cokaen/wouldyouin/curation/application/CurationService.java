package org.ktc2.cokaen.wouldyouin.curation.application;

import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.application.dto.CurationRequest;
import org.ktc2.cokaen.wouldyouin.curation.application.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationRepository;
import org.springframework.stereotype.Service;

@Service
public class CurationService {

    private final CurationRepository curationRepository;

    public CurationService(CurationRepository curationRepository) {
        this.curationRepository = curationRepository;
    }

    public CurationResponse create(CurationRequest curationRequest) {

        return CurationResponse.from(curationRepository.save(curationRequest.toEntity()));
    }

    public CurationResponse getById(Long curationId) {
        Curation target = curationRepository.findById(curationId)
            .orElseThrow(RuntimeException::new);
        return CurationResponse.from(target);
    }

    public List<CurationResponse> getAllByArea(Area area) {
        return curationRepository.findByArea(area).stream()
            .map(CurationResponse::from).toList();
    }

    public CurationResponse update(Long curationId, CurationRequest curationRequest) {
        Curation target = curationRepository.findById(curationId)
            .orElseThrow(RuntimeException::new);
        target.setFrom(curationRequest);
        return CurationResponse.from(target);
    }

    public void delete(Long curationId) {
        curationRepository.findById(curationId).orElseThrow(RuntimeException::new);
        curationRepository.deleteById(curationId);
    }
}
