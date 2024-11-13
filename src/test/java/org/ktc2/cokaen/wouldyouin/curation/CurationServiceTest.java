package org.ktc2.cokaen.wouldyouin.curation;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin.image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationCardService;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationService;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationRepository;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.CuratorService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurationServiceTest {

    private CurationService curationService;

    @Mock
    private CurationRepository curationRepository;

    @Mock
    private CuratorService curatorService;

    @Mock
    private EventService eventService;

    @Mock
    private CurationCardService curationCardService;

    @Mock
    private CurationImageService curationImageService;

    @Mock
    private MemberImage memberImage;

    private final long randomId = abs(new Random().nextLong());

    @BeforeEach
    void setUp() {
        curationService = new CurationService(curationRepository, curatorService, eventService, curationCardService, curationImageService);
    }

    @Test
    @DisplayName("큐레이션 ID를 통해 큐레이션을 찾지 못한 경우, 예외를 던진다.")
    void getById() {
        // given
        Curation validCuration = CurationData.curation.entity.get();
        given(curationRepository.findById(randomId)).willReturn(Optional.of(validCuration));

        // when
        CurationResponse response = curationService.getById(randomId);

        // then
        then(curationRepository).should(times(1)).findById(randomId);
        assertThat(response).usingRecursiveComparison().isEqualTo(CurationData.curation.response.get());
    }

    @Test
    @DisplayName("큐레이션 ID를 통해 큐레이션을 찾지 못한 경우, 예외를 던진다.")
    void getAllByAreaOrderByCreatedDateDesc() {
        // given


    }

    @Test
    void getAllByCuratorIdOrderByCreatedDateDesc() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}