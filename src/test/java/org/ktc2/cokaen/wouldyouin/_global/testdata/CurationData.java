package org.ktc2.cokaen.wouldyouin._global.testdata;

import static org.ktc2.cokaen.wouldyouin._global.TestData.EventDomain.createValidEvent;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._global.TestData;
import org.ktc2.cokaen.wouldyouin._global.TestData.EventDomain;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curation;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curationCard1;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curationCard2;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.curation1.entity;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.curation2;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.CurationCuratorResponse;
import org.springframework.test.util.ReflectionTestUtils;

public class CurationData {

    public static class R {
        public static class curationCard1 {
            public static final Long id = 351L;
            public static final String subtitle = "큐레이션 카드 부제목1";
            public static final String content = "큐레이션 카드 내용1 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationImage> images = List.of(entity.createValidCurationImage1());
            public static final List<Long> imageIds = List.of(1301L);
            public static final List<String> imageUrls = List.of("wouldyouin.com/curationImage1.jpg");
        }
        public static class curationCard2 {
            public static final Long id = 352L;
            public static final String subtitle = "큐레이션 카드 부제목2";
            public static final String content = "큐레이션 카드 내용2 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.";
            public static final List<Long> imageIds = List.of(1302L);
            public static final List<String> imageUrls = List.of("wouldyouin.com/curationImage2.jpg");
        }
        public static class curation {
            public static final Long id = 301L;
            public static final String title = "큐레이션 제목";
            public static final String content = "큐레이션 본문";
            public static final List<CurationCardRequest> curationCards = List.of(createValidCurationCard1Request());
            public static final Area area = Area.전체;
            public static final List<String> hashtags = List.of("#큐레이션", "#해시태그");
            public static final List<Long> eventIds = List.of(201L);
            public static final List<Event> events = List.of(createValidEvent());
            public static final LocalDateTime createdDate = LocalDateTime.of(2023, 3, 23, 0, 0);
            public static final LocalDateTime modifiedDate = LocalDateTime.of(2024, 3, 23, 0, 0);

        }
    }

    public static class dto {
        public static class
    }

    public static CurationCardRequest createValidCurationCard1Request() {
        return CurationCardRequest.builder()
            .subtitle(curationCard1.subtitle)
            .content(curationCard1.subtitle)
            .imageIds(curationCard1.imageIds)
            .build();
    }

    public static CurationCardRequest createValidCurationCard2Request() {
        return CurationCardRequest.builder()
            .subtitle(curationCard2.subtitle)
            .content(curationCard2.content)
            .imageIds(curationCard2.imageIds)
            .build();
    }

    public static CurationCard createValidCurationCard1() {
        CurationCard validCurationCard1 = CurationCard.builder()
            .subtitle(curationCard1.subtitle)
            .content(curationCard2.content)
//            .curation(createValidCuration())
            .images(curationCard1.images)
            .build();
        ReflectionTestUtils.setField(validCurationCard1, "id", curationCard1.id);
        return validCurationCard1;
    }

    public static CurationCard createValidCurationCard2() {
        CurationCard validCurationCard2 = CurationCard.builder()
            .subtitle(curationCard2.subtitle)
            .content(curationCard2.content)
//            .curation(createValidCuration())
            .images(List.of(curation2.entity.createValidCurationImage2()))
            .build();
        ReflectionTestUtils.setField(validCurationCard2, "id", curationCard2.id);
        return validCurationCard2;
    }

    public static CurationCardResponse createValidCurationCardResponse1() {
        return CurationCardResponse.builder()
            .subtitle(curationCard1.subtitle)
            .content(curationCard1.content)
            .imageUrls(curationCard1.imageUrls)
            .build();
    }

    public static CurationCardResponse createValidCurationCardResponse2() {
        return CurationCardResponse.builder()
            .subtitle(curationCard2.subtitle)
            .content(curationCard2.content)
            .imageUrls(curationCard2.imageUrls)
            .build();
    }

    public static CurationCreateRequest createValidCurationCreateRequest() {
        return CurationCreateRequest.builder()
            .title(curation.title)
            .content(curation.content)
            .curationCards(curation.curationCards)
            .area(curation.area)
            .hashtags(curation.hashtags)
            .eventIds(curation.eventIds)
            .build();
    }

    public static CurationEditRequest createValidCurationEditRequest() {
        return CurationEditRequest.builder()
            .title("큐레이션 제목 수정")
            .content("큐레이션 본문 수정")
            .curationCards(List.of(createValidCurationCard2Request()))
            .area(Area.광주)
            .hashtags(List.of("수정 해시태그"))
            .eventIds(List.of(202L))
            .build();
    }

    public static Curation createValidCuration() {
        Curation validCuration = Curation.builder()
            //.curator(createValidCurator())
            .title(curation.title)
            .content(curation.content)
            .curationCards(curation.curationCards)
            .area(curation.area)
            .hashtags(curation.hashtags)
            .events(curation.events)
            .build();
        ReflectionTestUtils.setField(validCuration, "id", curation.id);
        ReflectionTestUtils.setField(validCuration, "createdDate", curation.createdDate);
        ReflectionTestUtils.setField(validCuration, "modifiedDate", curation.modifiedDate);
        return validCuration;
    }

    public static CurationCuratorResponse createValidCurationCuratorResponse() {
        return CurationCuratorResponse.builder()
            .nickname("nick_curator_12")
            .email("curator1@example.com")
            .phone("010-4545-6767")
            .profileImageUrl("wouldyouin.com/memberImage1.jpg")
            .intro("큐레이터 자기소개입니다.")
            .likes(0)
            .hashtags(List.of("#큐레이터", "#해시태그", "#입니다"))
            .build();
    }

    public static CurationResponse createValidCurationResponse() {
        return CurationResponse.builder()
            .id(301L)
            .curator(createValidCurationCuratorResponse())
            .title("큐레이션 제목")
            .content("큐레이션 본문")
            .curationCards(List.of(createValidCurationCardResponse1()))
            .area(Area.전체)
            .hashtags(List.of("#큐레이션", "#해시태그"))
            .eventsInfo(List.of(EventDomain.createValidCurationEventResponse()))
            .createdTime(LocalDateTime.of(2023, 3, 23, 0, 0))
            .modifiedDate(LocalDateTime.of(2024, 3, 23, 0, 0))
            .build();
    }

    public static CurationSliceResponse createValidCurationSliceResponse() {
        return CurationSliceResponse.builder()
            .curations(List.of(createValidCurationResponse()))
            .sliceInfo(TestData.createSliceInfo())
            .build();
    }
}