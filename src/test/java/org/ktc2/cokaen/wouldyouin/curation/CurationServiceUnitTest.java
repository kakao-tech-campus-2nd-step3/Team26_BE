package org.ktc2.cokaen.wouldyouin.curation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curation1;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.event2;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.R.curation2;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationCardService;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationService;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationRepository;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.member.application.CuratorService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurationServiceUnitTest {

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

    @BeforeEach
    void setUp() {
        curationService = new CurationService(curationRepository, curatorService, eventService, curationCardService, curationImageService);
    }

    @Test
    @DisplayName("큐레이션 ID를 통해 해당 하는 큐레이션을 반환한다.")
    void getById() {
        // given
        given(curationRepository.findById(curation1.id)).willReturn(Optional.of(CurationData.curation1.entity.get()));
        given(curationImageService.getImageUrl(ImageData.curation1.entity.get())).willReturn(ImageData.R.curation1.url);

        // when
        CurationResponse response = curationService.getById(curation1.id);

        // then
        assertThat(response).isEqualTo(CurationData.curation1.response.get());
    }

    @Test
    @DisplayName("해당하는 지역의 모든 큐레이션을 반환한다.")
    void getAllByAreaOrderByCreatedDateDesc() {
        // given
        given(curationRepository.findAllByAreaOrderByCreatedDateDesc(curation1.area, curation1.lastId, curation1.pageable))
            .willReturn(CurationData.CurationSlice.get());
        given(curationImageService.getImageUrl(ImageData.curation1.entity.get())).willReturn(ImageData.R.curation1.url);

        // when
        CurationSliceResponse response =
            curationService.getAllByAreaOrderByCreatedDateDesc(curation1.area, curation1.pageable, curation1.lastId);

        // then
        assertThat(response).isEqualTo(CurationData.curation1.response.slice.get());
    }

    @Test
    @DisplayName("큐레이터 ID를 통해 해당하는 모든 큐레이션을 반환한다.")
    void getAllByCuratorIdOrderByCreatedDateDesc() {
        // given
        given(curationRepository.findAllByCuratorOrderByCreatedDateDesc(curator1.id, curation1.lastId, curation1.pageable))
            .willReturn(CurationData.CurationSlice.get());
        given(curationImageService.getImageUrl(ImageData.curation1.entity.get())).willReturn(ImageData.R.curation1.url);

        // when
        CurationSliceResponse response =
            curationService.getAllByCuratorIdOrderByCreatedDateDesc(curator1.id, curation1.pageable, curation1.lastId);

        // then
        assertThat(response).isEqualTo(CurationData.curation1.response.slice.get());
    }

    @Test
    @DisplayName("큐레이션 생성 DTO를 통해 큐레이션을 생성한다.")
    void create() {
        // given
        given(curatorService.getByIdOrThrow(curator1.id)).willReturn(MemberData.curator1.entity.get());
        given(curationCardService.create(CurationData.curationCard1.request.get()))
            .willReturn(CurationData.curationCard1.entity.get());
        given(eventService.getByIdOrThrow(curation1.eventIds.getFirst())).willReturn(EventData.event1.entity.get());
        given(curationRepository.save(any(Curation.class))).willReturn(CurationData.curation1.entity.get());
        given(curationImageService.getImageUrl(ImageData.curation1.entity.get())).willReturn(ImageData.R.curation1.url);

        // when
        CurationResponse response = curationService.create(curator1.memberIdentifier, CurationData.curation1.request.create.get());

        // then
        assertThat(response).isEqualTo(CurationData.curation1.response.get());
    }

    @Test
    @DisplayName("CurationCreateReqeust를 통해 큐레이션을 수정한다.")
    void update1() {
        // given
        given(curationRepository.findById(curation1.id)).willReturn(Optional.of(CurationData.curation1.entity.get()));
        given(curationCardService.create(CurationData.curationCard2.request.get()))
            .willReturn(CurationData.curationCard2.entity.get());
        given(eventService.getByIdOrThrow(event2.id)).willReturn(EventData.event1.entity.get());
        given(curationImageService.getImageUrl(ImageData.curation2.entity.get())).willReturn(curation2.url);
        given(curationImageService.createThumbnail(curation2.name)).willReturn(curation2.url);

        // when
        CurationResponse response = curationService.update(curator1.memberIdentifier, curation1.id,
            CurationData.curation1.request.edit.get());

        // then
        assertThat(response).isEqualTo(CurationData.curation2.response.get());
    }

    @Test
    @DisplayName("멤버 ID가 다르면 큐레이션을 수정할 수 없다.")
    void update2() {
        // given
        MemberIdentifier differentMember = new MemberIdentifier(100L, MemberType.curator);
        given(curationRepository.findById(curation1.id)).willReturn(Optional.of(CurationData.curation1.entity.get()));

        // when, then
        UnauthorizedException exception = assertThrows(
            UnauthorizedException.class, () -> curationService.update(differentMember, curation1.id, CurationData.curation1.request.edit.get()));
        assertThat(exception.getMessage()).isEqualTo("큐레이션에 접근할 권한이 없습니다.");
    }

    @Test
    @DisplayName("멤버 ID가 달라도 ADMIN은 큐레이션을 수정할 수 있다.")
    void update3() {
        // given
        Long invalidCuratorId = 100L;
        given(curationRepository.findById(curation1.id)).willReturn(Optional.of(CurationData.curation1.entity.get()));
        given(curationCardService.create(CurationData.curationCard2.request.get()))
            .willReturn(CurationData.curationCard2.entity.get());
        given(eventService.getByIdOrThrow(event2.id)).willReturn(EventData.event1.entity.get());
        given(curationImageService.getImageUrl(ImageData.curation2.entity.get())).willReturn(curation2.url);
        given(curationImageService.createThumbnail(curation2.name)).willReturn(curation2.url);

        // when
        CurationResponse response = curationService.update(new MemberIdentifier(invalidCuratorId, MemberType.admin), curation1.id,
            CurationData.curation1.request.edit.get());

        // then
        assertThat(response).isEqualTo(CurationData.curation2.response.get());
    }

    @Test
    @DisplayName("큐레이션 ID를 통해 해당하는 큐레이션을 삭제한다.")
    void delete1() {
        // given
        given(curationRepository.findById(curation1.id)).willReturn(Optional.of(CurationData.curation1.entity.get()));

        // when
        curationService.delete(curator1.memberIdentifier, curation1.id);

        // then
        then(curationCardService).should(times(curation1.curationCards.size())).delete(eq(curator1.memberIdentifier), any());
        then(curationRepository).should(times(1)).deleteById(curation1.id);
    }

    @Test
    @DisplayName("멤버 ID가 다르면 큐레이션을 삭제할 수 없다.")
    void delete2() {
        // given
        MemberIdentifier differentMember = new MemberIdentifier(100L, MemberType.curator);
        given(curationRepository.findById(curation1.id)).willReturn(Optional.of(CurationData.curation1.entity.get()));

        // when, then
        UnauthorizedException exception = assertThrows(
            UnauthorizedException.class, () -> curationService.delete(differentMember, curation1.id));
        assertThat(exception.getMessage()).isEqualTo("큐레이션에 접근할 권한이 없습니다.");
    }

    @Test
    @DisplayName("멤버 ID가 달라도 ADMIN은 큐레이션을 삭제할 수 있다.")
    void delete3() {
        // given
        MemberIdentifier admin = new MemberIdentifier(100L, MemberType.admin);
        given(curationRepository.findById(curation1.id)).willReturn(Optional.of(CurationData.curation1.entity.get()));

        // when
        curationService.delete(admin, curation1.id);

        // then
        then(curationCardService).should(times(curation1.curationCards.size())).delete(eq(admin), any());
        then(curationRepository).should(times(1)).deleteById(curation1.id);
    }
}