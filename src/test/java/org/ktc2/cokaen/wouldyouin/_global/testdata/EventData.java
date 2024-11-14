package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.CurationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReservationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.test.util.ReflectionTestUtils;

public class EventData {

    public static class R {

        public static class event1 {

            public static final long id = 201L;
            public static final String title = "행사 제목";
            public static final String content = "행사 내용입니다. 행사의 내용은 최소 20자 최대 1000자 입니다.";
            public static final Area area = Area.전체;
            public static final Location location = new Location(55.0, 43.0, "광주 북구 용봉로 77");
            public static final LocalDateTime startTime = LocalDateTime.now().plusDays(3)
                .plusHours(11).plusMinutes(47);
            public static final LocalDateTime endTime = startTime.plusWeeks(2);
            public static final Integer price = 15000;
            public static final Integer totalSeat = 100;
            public static final Integer leftSeat = 10;
            public static final Category category = Category.밴드;
            public static final String thumbnailUrl = ImageData.getThumbnailUrl(
                _Relation.images().getFirst());
            public static final LocalDateTime createdDate = LocalDateTime.now().minusDays(1);

            public static class _Relation {

                public static Host host() {
                    return MemberData.host1.entity.get();
                }

                public static List<EventImage> images() {
                    return List.of(
                        ImageData.event1.entity.get(),
                        ImageData.event2.entity.get(),
                        ImageData.event3.entity.get());
                }

                public static List<String> imageUrls() {
                    return List.of(
                        ImageData.R.event1.url,
                        ImageData.R.event2.url,
                        ImageData.R.event3.url);
                }
            }

            public static class _Dto {

                public static class editRequest1 {

                    public static final String title = "modifiedTitle";
                    public static final String content = "modifiedContent 조홍식씨 최소글자 20자라고 해놓고 안 지켰어요.";
                    public static final Area area = Area.광주;
                    public static final Location location = new Location(45.0, 143.0,
                        "광주 북구 용봉로 77");
                    public static final LocalDateTime startTime = event1.startTime.plusDays(2);
                    public static final LocalDateTime endTime = startTime.plusWeeks(3);
                    public static final int price = 20000;
                    public static final int totalSeat = 200;
                    public static final Category category = Category.뮤지컬;
                    public static final List<Long> imageIds = List.of(
                        ImageData.R.event4.id,
                        ImageData.R.event5.id
                    );
                }
            }
        }

        public static class event2 {

            public static final long id = 202L;
            public static final String title = "행사 제목";
            public static final String content = "행사 내용입니다. 행사의 내용은 최소 20자 최대 1000자 입니다.";
            public static final Area area = Area.전체;
            public static final Location location = new Location(55.0, 43.0, "광주 북구 용봉로 77");
            public static final LocalDateTime startTime = LocalDateTime.now().plusDays(3)
                .plusHours(11).plusMinutes(47);
            public static final LocalDateTime endTime = startTime.plusWeeks(2);
            public static final Integer price = 15000;
            public static final Integer totalSeat = 100;
            public static final Integer leftSeat = 10;
            public static final Category category = Category.밴드;
            public static final String thumbnailUrl = ImageData.getThumbnailUrl(
                _Relation.images().getFirst());
            public static final LocalDateTime createdDate = LocalDateTime.now().minusDays(1);

            public static class _Relation {

                public static Host host() {
                    return MemberData.host1.entity.get();
                }

                public static List<EventImage> images() {
                    return List.of(
                        ImageData.event1.entity.get(),
                        ImageData.event2.entity.get(),
                        ImageData.event3.entity.get());
                }

                public static List<String> imageUrls() {
                    return List.of(
                        ImageData.R.event1.url,
                        ImageData.R.event2.url,
                        ImageData.R.event3.url);
                }
            }

            public static class _Dto {

                public static class editRequest1 {

                    public static final String title = "modifiedTitle";
                    public static final String content = "modifiedContent 조홍식씨 최소글자 20자라고 해놓고 안 지켰어요. ";
                    public static final Area area = Area.광주;
                    public static final Location location = new Location(45.0, 143.0,
                        "광주 북구 용봉로 77");
                    public static final LocalDateTime startTime = event1.startTime.plusDays(2);
                    public static final LocalDateTime endTime = startTime.plusWeeks(3);
                    public static final int price = 20000;
                    public static final int totalSeat = 200;
                    public static final Category category = Category.뮤지컬;
                    public static final List<Long> imageIds = List.of(
                        ImageData.R.event4.id,
                        ImageData.R.event5.id
                    );
                }
            }
        }
    }

    public static class event1 {

        public static class entity {

            public static Event get() {
                Event ret = Event.builder()
                    .title(R.event1.title)
                    .content(R.event1.content)
                    .host(R.event1._Relation.host())
                    .area(R.event1.area)
                    .location(R.event1.location)
                    .startTime(R.event1.startTime)
                    .endTime(R.event1.endTime)
                    .price(R.event1.price)
                    .totalSeat(R.event1.totalSeat)
                    .category(R.event1.category)
                    .images(R.event1._Relation.images())
                    .thumbnailUrl(R.event1.thumbnailUrl)
                    .build();

                ReflectionTestUtils.setField(ret, "id", R.event1.id);
                ReflectionTestUtils.setField(ret, "leftSeat", R.event1.leftSeat);
                ret.getHost().setEvents(List.of(ret));
                ret.getImages().forEach(image -> image.setEvent(ret));
                return ret;
            }
        }

        public static class entityWithNoHost {

            public static Event get() {
                Event ret = Event.builder()
                    .title(R.event1.title)
                    .content(R.event1.content)
                    .area(R.event1.area)
                    .location(R.event1.location)
                    .startTime(R.event1.startTime)
                    .endTime(R.event1.endTime)
                    .price(R.event1.price)
                    .totalSeat(R.event1.totalSeat)
                    .category(R.event1.category)
                    .images(R.event1._Relation.images())
                    .thumbnailUrl(R.event1.thumbnailUrl)
                    .build();

                ReflectionTestUtils.setField(ret, "id", R.event1.id);
                ReflectionTestUtils.setField(ret, "leftSeat", R.event1.leftSeat);
                ret.getHost().setEvents(List.of(ret));
                ret.getImages().forEach(image -> image.setEvent(ret));
                return ret;
            }
        }

        public static class request {

            public static class create {

                public static EventCreateRequest get() {
                    return EventCreateRequest.builder()
                        .title(R.event1.title)
                        .content(R.event1.content)
                        .area(R.event1.area)
                        .location(R.event1.location)
                        .startTime(R.event1.startTime)
                        .endTime(R.event1.endTime)
                        .price(R.event1.price)
                        .totalSeat(R.event1.totalSeat)
                        .category(R.event1.category)
                        .imageIds(
                            R.event1._Relation.images().stream().map(EventImage::getId).toList())
                        .build();
                }
            }

            public static class edit1 {

                public static EventEditRequest get() {
                    return EventEditRequest.builder()
                        .title(R.event1._Dto.editRequest1.title)
                        .content(R.event1._Dto.editRequest1.content)
                        .area(R.event1._Dto.editRequest1.area)
                        .location(R.event1._Dto.editRequest1.location)
                        .startTime(R.event1._Dto.editRequest1.startTime)
                        .endTime(R.event1._Dto.editRequest1.endTime)
                        .price(R.event1._Dto.editRequest1.price)
                        .totalSeat(R.event1._Dto.editRequest1.totalSeat)
                        .category(R.event1._Dto.editRequest1.category)
                        .imageIds(R.event1._Dto.editRequest1.imageIds)
                        .build();
                }
            }
        }

        public static class response {

            public static EventResponse get() {
                return EventResponse.from(EventData.event1.entity.get(),
                    R.event1._Relation.imageUrls());
            }
        }
    }

    public static class response {

        public static class reservationEvent {

            public static ReservationEventResponse createValidReservationEventResponse() {
                return ReservationEventResponse.from(EventData.event1.entity.get());
            }
        }

        public static class reviewEvent {

            public static ReviewEventResponse createValidReviewEventResponse() {
                return ReviewEventResponse.from(EventData.event1.entity.get());
            }
        }

        public static class curationEvent {

            public static CurationEventResponse createValidCurationEventResponse() {
                return CurationEventResponse.from(EventData.event1.entity.get());
            }
        }
    }
}
