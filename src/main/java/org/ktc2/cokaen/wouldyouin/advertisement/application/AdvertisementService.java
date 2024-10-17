package org.ktc2.cokaen.wouldyouin.advertisement.application;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.advertisement.api.AdvertisementRequest;
import org.ktc2.cokaen.wouldyouin.advertisement.api.AdvertisementResponse;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.AdvertisementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor()
public class AdvertisementService implements EntityGettable<Long, Advertisement> {

    private final AdvertisementRepository advertisementRepository;

    @Transactional(readOnly = true)
    public List<AdvertisementResponse> getAllActiveAdvertisements() {
        return advertisementRepository.findByCurrentTime(LocalDateTime.now()).stream()
            .map(AdvertisementResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Advertisement getByIdOrThrow(Long id) throws RuntimeException {
        return advertisementRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public AdvertisementResponse getAdvertisementByAdId(Long adId) {
        return AdvertisementResponse.from(getByIdOrThrow(adId));
    }

    @Transactional
    public AdvertisementResponse create(AdvertisementRequest advertisementRequest) {
        return AdvertisementResponse.from(
            advertisementRepository.save(advertisementRequest.toEntity()));
    }

    @Transactional
    public AdvertisementResponse update(Long adId, AdvertisementRequest advertisementRequest) {
        Advertisement target = advertisementRepository.findById(adId)
            .orElseThrow(RuntimeException::new);
        target.updateFrom(advertisementRequest);
        return AdvertisementResponse.from(target);
    }

    @Transactional
    public void delete(Long adId) {
        advertisementRepository.findById(adId).orElseThrow(RuntimeException::new);
        advertisementRepository.deleteById(adId);
    }

}
