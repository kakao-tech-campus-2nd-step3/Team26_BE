package org.ktc2.cokaen.wouldyouin.curation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curationCard1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.R.curation1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationCardService;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCardRepository;
import org.ktc2.cokaen.wouldyouin.image.application.CurationImageService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CurationCardServiceTest {

    private CurationCardService curationCardService;

    @Mock
    private CurationCardRepository curationCardRepository;

    @Mock
    private CurationImageService curationImageService;

    @BeforeEach
    void setUp() {
        curationCardService = new CurationCardService(curationCardRepository, curationImageService);
    }

    @Test
    @DisplayName("큐레이션 ID를 통해 해당 하는 큐레이션을 반환한다.")
    void getById() {
        // given
        given(curationCardRepository.findById(curationCard1.id)).willReturn(Optional.of(CurationData.curationCard1.entity.get()));
        given(curationImageService.getImageUrl(ImageData.curation1.entity.get())).willReturn(ImageData.R.curation1.url);

        // when
        CurationCardResponse response = curationCardService.getById(curationCard1.id);

        // then
        assertThat(response).isEqualTo(CurationData.curationCard1.response.get());
    }

    @Test
    @DisplayName("큐레이션 카드 DTO를 통해 큐레이션 카드를 생성한다.")
    void create() {
        // given
        given(curationImageService.getById(curation1.id)).willReturn(ImageData.curation1.entity.get());
        given(curationCardRepository.save(CurationData.curationCard1.entityWithNoId.get()))
            .willReturn(CurationData.curationCard1.entity.get());

        // when
        CurationCard response = curationCardService.create(CurationData.curationCard1.request.get());

        // then
        assertThat(response).isEqualTo(CurationData.curationCard1.entity.get());
    }

    @Test
    @DisplayName("큐레이션 카 ID를 통해 해당 하는 큐레이션 카드를 삭제한다.")
    void delete() {
        // given
        given(curationCardRepository.findById(curationCard1.id)).willReturn(Optional.of(CurationData.curationCard1.entity.get()));

        // when
        curationCardService.delete(curator1.memberIdentifier, curationCard1.id);

        // then
        then(curationImageService).should(times(curationCard1.images.size())).deleteImage(any(), any());
        then(curationCardRepository).should(times(1)).deleteById(curationCard1.id);
    }
}
