package org.ktc2.cokaen.wouldyouin.advertisement.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementRequest;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementResponse;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.AdvertisementRepository;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.image.application.AdvertisementImageService;
import org.ktc2.cokaen.wouldyouin.image.persist.AdvertisementImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository adRepository;
    private final AdvertisementImageService adImageService;

    @Transactional(readOnly = true)
    public AdvertisementResponse getById(Long adId) {
        return AdvertisementResponse.from(getByIdOrThrow(adId));
    }

    @Transactional(readOnly = true)
    public List<AdvertisementResponse> getAllActiveAdvertisements() {
        return adRepository.findAllActiveAdvertisements(LocalDateTime.now()).stream()
            .map(AdvertisementResponse::from).toList();
    }

    @Transactional
    public AdvertisementResponse create(AdvertisementRequest adRequest, MultipartFile image) {
        AdvertisementImage adImage = adImageService.saveImage(image);
        Advertisement ad = adRepository.save(adRequest.toEntity(adImage));
        adImageService.setAdvertisement(adImage, ad);
        return AdvertisementResponse.from(ad);
    }

    @Transactional
    public void delete(MemberIdentifier identifier, Long adId) {
        Advertisement ad = getByIdOrThrow(adId);
        adImageService.deleteImage(identifier, ad.getAdvertisementImage().getId());
        adRepository.deleteById(adId);
    }

    private Advertisement getByIdOrThrow(Long adId) {
        return adRepository.findById(adId)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 광고를 찾을 수 없습니다."));
    }
}