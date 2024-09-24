package org.ktc2.cokaen.wouldyouin.service;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.controller.curation.CurationRequest;
import org.ktc2.cokaen.wouldyouin.controller.curation.CurationResponse;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Curation;
import org.ktc2.cokaen.wouldyouin.repository.CurationRepository;
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
