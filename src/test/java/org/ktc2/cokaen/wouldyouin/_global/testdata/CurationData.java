package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.springframework.test.util.ReflectionTestUtils;

public class CurationData {

    public static class R {
        public static class curationCard1 {
            public static final Long id = 351L;
            public static final String subtitle = "큐레이션 카드 부제목1";
            public static final String content = "큐레이션 카드 내용1 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationImage> images = List.of(ImageData.curation1.entity.get());
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
            public static final String content = "큐레이션 본문 입니다. 큐레이션의 본문은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationCard> curationCards = List.of(
                CurationData.curationCard1.entity.get(),
                CurationData.curationCard2.entity.get()
            );
            public static final List<CurationCardRequest> curationCardRequests = List.of(
                CurationData.curationCard1.request.get(),
                CurationData.curationCard2.request.get()
            );
            public static final List<CurationCardResponse> curationCardResponses = List.of(
                CurationData.curationCard1.response.get(),
                CurationData.curationCard2.response.get()
            );
            public static final Area area = Area.전체;
            public static final List<String> hashtags = List.of("#큐레이션", "#해시태그");
            public static final List<Long> eventIds = List.of(201L);
            public static final List<Event> events = List.of(EventData.event1.entity.get());
            public static final LocalDateTime createdDate = LocalDateTime.of(2023, 3, 23, 0, 0);
            public static final LocalDateTime modifiedDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curationEditRequest {
            public static final String title = "큐레이션 제목 수정";
            public static final String content = "수정된 큐레이션 본문 입니다. 큐레이션의 본문은 최소 20자 최대 1000자 입니다.";
            public static final List<CurationCardRequest> curationCardRequests = List.of(
                CurationData.curationCard2.request.get()
            );
            public static final Area area = Area.광주;
            public static final List<String> hashtags = List.of("수정 해시태그");
            public static final List<Long> eventIds = List.of(202L);
        }
    }

    public static class curationCard1 {
        public static class entity {
            public static CurationCard get() {
                CurationCard validCurationCard1 = CurationCard.builder()
                    .subtitle(R.curationCard1.subtitle)
                    .content(R.curationCard1.content)
        //            .curation(createValidCuration())
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
                    //            .curation(createValidCuration())
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

    public static class curation {
        public static class entity {
            public static Curation get() {
                Curation validCuration = Curation.builder()
                    .curator(MemberData.curator1.entity.get())
                    .title(R.curation.title)
                    .content(R.curation.content)
                    .curationCards(R.curation.curationCards)
                    .area(R.curation.area)
                    .hashtags(R.curation.hashtags)
                    .events(R.curation.events)
                    .build();
                ReflectionTestUtils.setField(validCuration, "id", R.curation.id);
                ReflectionTestUtils.setField(validCuration, "createdDate", R.curation.createdDate);
                ReflectionTestUtils.setField(validCuration, "modifiedDate", R.curation.modifiedDate);
                return validCuration;
            }
        }
        public static class request {
            public static class create {
                public static CurationCreateRequest get() {
                    return CurationCreateRequest.builder()
                        .title(R.curation.title)
                        .content(R.curation.content)
                        .curationCards(R.curation.curationCardRequests)
                        .area(R.curation.area)
                        .hashtags(R.curation.hashtags)
                        .eventIds(R.curation.eventIds)
                        .build();
                }
            }
            public static class edit {
                public static CurationEditRequest get() {
                    return CurationEditRequest.builder()
                        .title(curationEditRequest.title)
                        .content(curationEditRequest.content)
                        .curationCards(curationEditRequest.curationCardRequests)
                        .area(curationEditRequest.area)
                        .hashtags(curationEditRequest.hashtags)
                        .eventIds(curationEditRequest.eventIds)
                        .build();
                }
            }
        }
        public static class response {
            public static CurationResponse get() {
                return CurationResponse.builder()
                    .id(R.curation.id)
                    //.curator(MemberData.dto.response.curationCurator.get())
                    .title(R.curation.title)
                    .content(R.curation.content)
                    .curationCards(R.curation.curationCardResponses)
                    .area(R.curation.area)
                    .hashtags(R.curation.hashtags)
                    //.eventsInfo(List.of(EventDomain.createValidCurationEventResponse()))
                    .createdTime(R.curation.createdDate)
                    .modifiedDate(R.curation.modifiedDate)
                    .build();
            }
            public static class slice {
                public static CurationSliceResponse get() {
                    return CurationSliceResponse.builder()
                        .curations(List.of(response.get()))
                        .sliceInfo(CommonData.sliceInfo.get())
                        .build();
                }
            }
        }
    }
}