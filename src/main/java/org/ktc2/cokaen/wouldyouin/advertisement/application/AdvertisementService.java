package org.ktc2.cokaen.wouldyouin.advertisement.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.AdvertisementImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementRequest;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementResponse;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.AdvertisementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor()
public class AdvertisementService {

    private final AdvertisementRepository adRepository;
    private final AdvertisementImageService adImageService;

    @Transactional(readOnly = true)
    public List<AdvertisementResponse> getAllActiveAdvertisements() {
        return adRepository.findByCurrentTime(LocalDateTime.now()).stream()
            .map(AdvertisementResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Advertisement getByIdOrThrow(Long id) throws RuntimeException {
        return adRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public AdvertisementResponse getAdvertisementByAdId(Long adId) {
        return AdvertisementResponse.from(adRepository.findById(adId).orElseThrow(() -> new EntityNotFoundException("Advertisement")));
    }

    // Todo: 롤백될 경우, 저장한 이미지 삭제
    @Transactional
    public AdvertisementResponse create(AdvertisementRequest adRequest, MultipartFile image) {
        AdvertisementImage adImage = adImageService.saveAndCreateImage(image);
        Advertisement ad = adRepository.save(adRequest.toEntity(adImage));
        adImage.setAdvertisement(ad);
        return AdvertisementResponse.from(ad);
    }

    // Todo: 수정될 때 이미지가 null인 경우 기존 이미지로 대체하는 로직 프론트와 협의
    // Todo: 롤백될 경우, 저장한 이미지 삭제
    @Transactional
    public AdvertisementResponse update(Long adId, AdvertisementRequest adRequest, MultipartFile multipartFile) {
        Advertisement target = adRepository.findById(adId).orElseThrow(() -> new EntityNotFoundException("Advertisement"));
        Optional.ofNullable(multipartFile).ifPresentOrElse(
            image -> {
                adImageService.deleteAndDelete(target.getAdvertisementImage().getId());
                AdvertisementImage adImage = adImageService.saveAndCreateImage(image);
                target.updateFrom(adRequest, adImage);
                adImage.setAdvertisement(target);
            },
            () -> {
                AdvertisementImage adImage = target.getAdvertisementImage();
                target.updateFrom(adRequest, adImage);
            }
        );
        return AdvertisementResponse.from(target);
    }

    @Transactional
    public void delete(Long adId) {
        adRepository.findById(adId).orElseThrow(() -> new EntityNotFoundException("Advertisement"));
        adRepository.deleteById(adId);
    }
}