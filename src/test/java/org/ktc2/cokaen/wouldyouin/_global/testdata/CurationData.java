package org.ktc2.cokaen.wouldyouin._global.testdata;

import static org.ktc2.cokaen.wouldyouin._global.testdata.EventData.response.curationEvent.createValidCurationEventResponse;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.curation1.entity;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.event1;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.event2;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationCardImage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

public class CurationData {

    public static class R {

        public static class curationCard1 {

            public static final Long id = 351L;
            public static final String subtitle = "큐레이션 카드 부제목1";
            public static final String content = "큐레이션 카드 내용1 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationCardImage> images = List.of(ImageData.curation1.entity.get());
            public static final List<Long> imageIds = List.of(ImageData.R.curation1.id);
            public static final List<String> imageUrls = List.of(ImageData.R.curation1.url);
        }

        public static class curationCard2 {

            public static final Long id = 352L;
            public static final String subtitle = "큐레이션 카드 부제목2";
            public static final String content = "큐레이션 카드 내용2 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationCardImage> images = List.of(ImageData.curation2.entity.get());
            public static final List<Long> imageIds = List.of(ImageData.R.curation2.id);
            public static final List<String> imageUrls = List.of(ImageData.R.curation2.url);
        }

        public static class curation1 {

            public static final Long id = 301L;
            public static final String title = "큐레이션 제목1";
            public static final String content = "큐레이션 본문1 입니다. 큐레이션의 본문은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationCard> curationCards = List.of(
                CurationData.curationCard1.entity.get()
            );
            public static final List<CurationCardRequest> curationCardRequests = List.of(
                CurationData.curationCard1.request.get()
            );
            public static final List<CurationCardResponse> curationCardResponses = List.of(
                CurationData.curationCard1.response.get()
            );
            public static final int page = 0;
            public static final int pageSize = 10;
            public static final Long lastId = 100L;
            public static final PageRequest pageable = PageRequest.of(0, 10);
            public static final Area area = Area.전체;
            public static final List<String> hashtags = List.of("#큐레이션", "#해시태그");
            public static final List<Long> eventIds = List.of(event1.id);
            public static final List<Event> events = List.of(EventData.event1.entity.get());
            public static final LocalDateTime createdDate = LocalDateTime.of(2023, 3, 23, 0, 0);
            public static final LocalDateTime modifiedDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }

        public static class curation2 {

            public static final Long id = 301L;
            public static final String title = "큐레이션 제목 수정";
            public static final String content = "수정된 큐레이션 본문 입니다. 큐레이션의 본문은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationCard> curationCards = List.of(
                CurationData.curationCard2.entity.get()
            );
            public static final List<CurationCardRequest> curationCardRequests = List.of(
                CurationData.curationCard2.request.get()
            );
            public static final List<CurationCardResponse> curationCardResponses = List.of(
                CurationData.curationCard2.response.get()
            );
            public static final int page = 0;
            public static final int pageSize = 20;
            public static final Long lastId = 50L;
            public static final PageRequest pageable = PageRequest.of(0, 10);
            public static final Area area = Area.광주;
            public static final List<String> hashtags = List.of("수정 해시태그");
            public static final List<Long> eventIds = List.of(event2.id);
            public static final List<Event> events = List.of(EventData.event1.entity.get());
            public static final LocalDateTime createdDate = LocalDateTime.of(2023, 3, 23, 0, 0);
            public static final LocalDateTime modifiedDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
    }

    public static class curationCard1 {

        public static class entity {

            public static CurationCard get() {
                CurationCard validCurationCard1 = CurationCard.builder()
                    .subtitle(R.curationCard1.subtitle)
                    .content(R.curationCard1.content)
                    .curation(CurationData.curation1.entity.get())
                    .images(R.curationCard1.images)
                    .build();
                ReflectionTestUtils.setField(validCurationCard1, "id", R.curationCard1.id);
                return validCurationCard1;
            }
        }

        public static class entityWithNoId {

            public static CurationCard get() {
                return CurationCard.builder()
                    .subtitle(R.curationCard1.subtitle)
                    .content(R.curationCard1.content)
                    .images(R.curationCard1.images)
                    .build();
            }
        }

        public static class entityWithNoCuration {

            public static CurationCard get() {
                CurationCard validCurationCard1 = CurationCard.builder()
                    .subtitle(R.curationCard1.subtitle)
                    .content(R.curationCard1.content)
                    .images(R.curationCard1.images)
                    .build();
                ReflectionTestUtils.setField(validCurationCard1, "id", R.curationCard1.id);
                return validCurationCard1;
            }
        }

        public static class request {

            public static CurationCardRequest get() {
                return CurationCardRequest.builder()
                    .subtitle(R.curationCard1.subtitle)
                    .content(R.curationCard1.content)
                    .imageIds(R.curationCard1.imageIds)
                    .build();
            }
        }

        public static class response {

            public static CurationCardResponse get() {
                return CurationCardResponse.builder()
                    .subtitle(R.curationCard1.subtitle)
                    .content(R.curationCard1.content)
                    .imageUrls(R.curationCard1.imageUrls)
                    .build();
            }
        }
    }

    public static class curationCard2 {

        public static class entity {

            public static CurationCard get() {
                CurationCard validCurationCard2 = CurationCard.builder()
                    .subtitle(R.curationCard2.subtitle)
                    .content(R.curationCard2.content)
                    .curation(CurationData.curation1.entityWithNoCurationCard.get())
                    .images(List.of(ImageData.curation2.entity.get()))
                    .build();
                ReflectionTestUtils.setField(validCurationCard2, "id", R.curationCard2.id);
                return validCurationCard2;
            }
        }

        public static class entityWithNoCuration {

            public static CurationCard get() {
                CurationCard validCurationCard2 = CurationCard.builder()
                    .subtitle(R.curationCard2.subtitle)
                    .content(R.curationCard2.content)
                    .images(List.of(ImageData.curation2.entity.get()))
                    .build();
                ReflectionTestUtils.setField(validCurationCard2, "id", R.curationCard2.id);
                return validCurationCard2;
            }
        }

        public static class request {

            public static CurationCardRequest get() {
                return CurationCardRequest.builder()
                    .subtitle(R.curationCard2.subtitle)
                    .content(R.curationCard2.content)
                    .imageIds(R.curationCard2.imageIds)
                    .build();
            }
        }

        public static class response {

            public static CurationCardResponse get() {
                return CurationCardResponse.builder()
                    .subtitle(R.curationCard2.subtitle)
                    .content(R.curationCard2.content)
                    .imageUrls(R.curationCard2.imageUrls)
                    .build();
            }
        }
    }

    public static class curation1 {

        public static class entity {

            public static Curation get() {
                Curation validCuration = Curation.builder()
                    .curator(MemberData.curator1.entity.get())
                    .title(R.curation1.title)
                    .content(R.curation1.content)
                    .curationCards(R.curation1.curationCards)
                    .area(R.curation1.area)
                    .hashtags(R.curation1.hashtags)
                    .events(R.curation1.events)
                    .build();
                ReflectionTestUtils.setField(validCuration, "id", R.curation1.id);
                ReflectionTestUtils.setField(validCuration, "createdDate", R.curation1.createdDate);
                ReflectionTestUtils.setField(validCuration, "modifiedDate", R.curation1.modifiedDate);
                return validCuration;
            }
        }

        public static class entityWithNoCurator {

            public static Curation get() {
                Curation validCuration = Curation.builder()
                    .title(R.curation1.title)
                    .content(R.curation1.content)
                    .curationCards(R.curation1.curationCards)
                    .area(R.curation1.area)
                    .hashtags(R.curation1.hashtags)
                    .events(R.curation1.events)
                    .build();
                ReflectionTestUtils.setField(validCuration, "id", R.curation1.id);
                ReflectionTestUtils.setField(validCuration, "createdDate", R.curation1.createdDate);
                ReflectionTestUtils.setField(validCuration, "modifiedDate", R.curation1.modifiedDate);
                return validCuration;
            }
        }

        public static class entityWithNoCurationCard {

            public static Curation get() {
                Curation validCuration = Curation.builder()
                    .curator(MemberData.curator1.entity.get())
                    .title(R.curation1.title)
                    .content(R.curation1.content)
                    .area(R.curation1.area)
                    .hashtags(R.curation1.hashtags)
                    .events(R.curation1.events)
                    .build();
                ReflectionTestUtils.setField(validCuration, "id", R.curation1.id);
                ReflectionTestUtils.setField(validCuration, "createdDate", R.curation1.createdDate);
                ReflectionTestUtils.setField(validCuration, "modifiedDate", R.curation1.modifiedDate);
                return validCuration;
            }
        }

        public static class entityWithNoEvent {

            public static Curation get() {
                Curation validCuration = Curation.builder()
                    .curator(MemberData.curator1.entity.get())
                    .title(R.curation1.title)
                    .content(R.curation1.content)
                    .curationCards(R.curation1.curationCards)
                    .area(R.curation1.area)
                    .hashtags(R.curation1.hashtags)
                    .events(List.of())
                    .build();
                ReflectionTestUtils.setField(validCuration, "id", R.curation1.id);
                ReflectionTestUtils.setField(validCuration, "createdDate", R.curation1.createdDate);
                ReflectionTestUtils.setField(validCuration, "modifiedDate", R.curation1.modifiedDate);
                return validCuration;
            }
        }

        public static class request {

            public static class create {

                public static CurationCreateRequest get() {
                    return CurationCreateRequest.builder()
                        .title(R.curation1.title)
                        .content(R.curation1.content)
                        .curationCards(R.curation1.curationCardRequests)
                        .area(R.curation1.area)
                        .hashtags(R.curation1.hashtags)
                        .eventIds(R.curation1.eventIds)
                        .build();
                }
            }

            public static class edit {

                public static CurationEditRequest get() {
                    return CurationEditRequest.builder()
                        .title(R.curation2.title)
                        .content(R.curation2.content)
                        .curationCards(R.curation2.curationCardRequests)
                        .area(R.curation2.area)
                        .hashtags(R.curation2.hashtags)
                        .eventIds(R.curation2.eventIds)
                        .build();
                }
            }
        }

        public static class response {

            public static CurationResponse get() {
                return CurationResponse.builder()
                    .id(R.curation1.id)
                    .curator(MemberData.response.curation1Curator1.get())
                    .title(R.curation1.title)
                    .content(R.curation1.content)
                    .curationCards(R.curation1.curationCardResponses)
                    .area(R.curation1.area)
                    .hashtags(R.curation1.hashtags)
                    .eventsInfo(List.of(createValidCurationEventResponse()))
                    .createdTime(R.curation1.createdDate)
                    .modifiedDate(R.curation1.modifiedDate)
                    .build();
            }

            public static class slice {

                public static CurationSliceResponse get() {
                    return CurationSliceResponse.builder()
                        .curations(List.of(response.get()))
                        .sliceInfo(CommonData.sliceInfo.curation.get())
                        .build();
                }
            }
        }
    }

    public static class curation2 {

        public static class entity {

            public static Curation get() {
                Curation validCuration = Curation.builder()
                    .curator(MemberData.curator1.entity.get())
                    .title(R.curation2.title)
                    .content(R.curation2.content)
                    .curationCards(List.of(curationCard2.entity.get()))
                    .area(R.curation2.area)
                    .hashtags(R.curation2.hashtags)
                    .events(R.curation1.events)
                    .build();
                ReflectionTestUtils.setField(validCuration, "id", R.curation2.id);
                ReflectionTestUtils.setField(validCuration, "createdDate", R.curation2.createdDate);
                ReflectionTestUtils.setField(validCuration, "modifiedDate", R.curation2.modifiedDate);
                return validCuration;
            }
        }

        public static class response {

            public static CurationResponse get() {
                return CurationResponse.builder()
                    .id(R.curation2.id)
                    .curator(MemberData.response.curation1Curator1.get())
                    .title(R.curation2.title)
                    .content(R.curation2.content)
                    .curationCards(R.curation2.curationCardResponses)
                    .area(R.curation2.area)
                    .hashtags(R.curation2.hashtags)
                    .eventsInfo(List.of(createValidCurationEventResponse()))
                    .createdTime(R.curation2.createdDate)
                    .modifiedDate(R.curation2.modifiedDate)
                    .thumbnailUrl(ImageData.R.curation2.url)
                    .build();
            }
        }
    }

    public static class CurationSlice {

        public static Slice<Curation> get() {
            return new SliceImpl<>(List.of(entity.get()), R.curation1.pageable, true);
        }
    }
}