package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.CurationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReservationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.springframework.test.util.ReflectionTestUtils;

public class EventData {

    public static class R {

        public static class event {
            public static final long id = 201L;
            public static final String title = "행사 제목";
            public static final String content = "행사 내용입니다. 행사의 내용은 최소 20자 최대 1000자 입니다.";
            public static final Area area = Area.전체;
            public static final Location location = new Location(55.0, 43.0, "광주 북구 용봉로 77");
            public static final LocalDateTime startTime = LocalDateTime.now().plusDays(3).plusHours(11).plusMinutes(47);
            public static final LocalDateTime endTime = startTime.plusWeeks(2);
            public static final int price = 15000;
            public static final int totalSeat = 100;
            public static final Category category = Category.밴드;

            public static final String hostNickname = MemberData.R.host.nickname;
            public static final String hostProfileImageUrl = ImageData.R.member.host.url;
            public static final String thumbnailImageUrl = "ImageData.R.event.thumbnail.url";
        }
        public static class eventEditRequest {
            public static final String title = "modifiedTitle";
            public static final String content = "modifiedContent 조홍식씨 최소글자 20자라고 해놓고 안 지켰어요. ";
            public static final Area area = Area.광주;
            public static final Location location = new Location(45.0, 143.0, "광주 북구 용봉로 77");
            public static final LocalDateTime startTime = event.startTime.plusDays(2);
            public static final LocalDateTime endTime = startTime.plusWeeks(3);
            public static final int price = 20000;
            public static final int totalSeat = 200;
            public static final Category category = Category.뮤지컬;
        }
    }

    public static class event {
        public static class entity {
            public static Event get() {
                Event ret = Event.builder()
                    .title(R.event.title)
                    .content(R.event.content)
                    .area(R.event.area)
                    .location(R.event.location)
                    .startTime(R.event.startTime)
                    .endTime(R.event.endTime)
                    .price(R.event.price)
                    .totalSeat(R.event.totalSeat)
                    .category(R.event.category)
                    .build();
                ret.setHost(MemberData.host.entity.get());
                ReflectionTestUtils.setField(ret, "id", R.event.id);
                return ret;
            }
        }
        public static class request {
            public static class create {
                public static EventCreateRequest get() {
                    return EventCreateRequest.builder()
                        .title(R.event.title)
                        .content(R.event.content)
                        .area(R.event.area)
                        .location(R.event.location)
                        .startTime(R.event.startTime)
                        .endTime(R.event.endTime)
                        .price(R.event.price)
                        .totalSeat(R.event.totalSeat)
                        .category(R.event.category)
                        .imageIds(List.of())
                        .build();
                }
            }
            public static class edit {
                public static EventEditRequest get() {
                    return EventEditRequest.builder()
                        .title(R.eventEditRequest.title)
                        .content(R.eventEditRequest.content)
                        .area(R.eventEditRequest.area)
                        .location(R.eventEditRequest.location)
                        .startTime(R.eventEditRequest.startTime)
                        .endTime(R.eventEditRequest.endTime)
                        .price(R.eventEditRequest.price)
                        .totalSeat(R.eventEditRequest.totalSeat)
                        .category(R.eventEditRequest.category)
                        .imageIds(List.of())
                        .build();
                }
            }
        }
        public static class response {

        }
    }

    public static class response {
        public static class reservationEvent {
            public static ReservationEventResponse createValidReservationEventResponse() {
                return ReservationEventResponse.builder()
                    .eventId(R.event.id)
                    .title(R.event.title)
                    .price(R.event.price)
                    .build();
            }
        }
        public static class curationEvent {
            public static CurationEventResponse createValidCurationEventResponse() {
                return CurationEventResponse.builder()
                    .eventId(R.event.id)
                    .title(R.event.title)
                    .location(R.event.location)
                    .thumbnailImageUrl(R.event.thumbnailImageUrl)
                    .hostProfileImageUrl(R.event.hostProfileImageUrl)
                    .hostNickname(R.event.hostNickname)
                    .build();
            }
        }
    }

}
