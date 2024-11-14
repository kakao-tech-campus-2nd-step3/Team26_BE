package org.ktc2.cokaen.wouldyouin.image;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curation1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData;
import org.ktc2.cokaen.wouldyouin.image.application.AdvertisementImageService;
import org.ktc2.cokaen.wouldyouin.image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.image.application.ImageStorageService;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationImageRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CurationImageServiceTest {

    private CurationImageService curationImageService;

    @Mock
    private ImageStorageService imageStorageService;

    @Mock
    private CurationImageRepository curationImageRepository;

    @BeforeEach
    void setUp() {
        curationImageService = new CurationImageService(imageStorageService, curationImageRepository);
    }

    @Test
    @DisplayName("이미지 ID를 통해 큐레이션 이미지를 반환한다.")
    void getById() {
        // given
        given(curationImageRepository.findById(curation1.id)).willReturn(Optional.of(ImageData.curation1.entity.get()));

        // when
        CurationImage response = curationImageService.getById(curation1.id);

        // then
        assertThat(response).isEqualTo(ImageData.curation1.entity.get());
    }

//    @Test
//    @DisplayName("이미지 ID를 통해 해당 이미지를 삭제한다.")
//    void deleteImage() {
//        // given
//        CurationImage image = ImageData.curation1.entity.get();
//        given(curationImageRepository.findById(curation1.id)).willReturn(Optional.of(image));
//
//        // when, then
//        then(imageStorageService).should(times(1)).delete(eq(image.getName()), any());
//    }
}
