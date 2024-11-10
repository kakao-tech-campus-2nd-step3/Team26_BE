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
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository adRepository;
    private final AdvertisementImageService adImageService;

    @Transactional
    public Advertisement getByIdOrThrow(Long adId) {
        return adRepository.findById(adId)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 광고를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public AdvertisementResponse getAdvertisementByAdId(Long adId) {
        return AdvertisementResponse.from(getByIdOrThrow(adId));
    }

    @Transactional(readOnly = true)
    public List<AdvertisementResponse> getAllActiveAdvertisements() {
        return adRepository.findAllActiveAdvertisements(LocalDateTime.now()).stream()
            .map(AdvertisementResponse::from).toList();
    }

    // Todo: 롤백될 경우, 저장한 이미지 삭제
    @Transactional
    public AdvertisementResponse create(AdvertisementRequest adRequest, MultipartFile image) {
        AdvertisementImage adImage = adImageService.saveImage(image);
        Advertisement ad = adRepository.save(adRequest.toEntity(adImage));
        adImageService.setAdvertisement(adImage, ad);
        return AdvertisementResponse.from(ad);
    }

    // Todo: 수정될 때 이미지가 null인 경우 기존 이미지로 대체하는 로직 프론트와 협의
    // Todo: 롤백될 경우, 저장한 이미지 삭제
    @Transactional
    public AdvertisementResponse update(Long adId, AdvertisementRequest adRequest, MultipartFile multipartFile) {
        Advertisement ad = getByIdOrThrow(adId);
        Optional.ofNullable(multipartFile).ifPresentOrElse(
            image -> {
                adImageService.deleteImage(ad.getAdvertisementImage().getId());
                AdvertisementImage adImage = adImageService.saveImage(image);
                ad.updateFrom(adRequest, adImage);
                adImage.setAdvertisement(ad);
            },
            () -> {
                AdvertisementImage adImage = ad.getAdvertisementImage();
                ad.updateFrom(adRequest, adImage);
            }
        );
        return AdvertisementResponse.from(ad);
    }

    @Transactional
    public void delete(Long adId) {
        Advertisement ad = getByIdOrThrow(adId);
        adImageService.deleteImage(ad.getAdvertisementImage().getId());
        adRepository.deleteById(adId);
    }
}