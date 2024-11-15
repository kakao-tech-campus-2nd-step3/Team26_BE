package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.util.List;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.event1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.normal1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.response.review1Member1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData.R.review1._Dto.editRequest1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData.review1.response1;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.ReviewMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewCreateRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewEditRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewResponse;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewSliceResponse;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

public class ReviewData {

    public static class R {

        public static class review1 {

            public static final Long id = 1L;
            public static final Integer score = 3;
            public static final ReviewMemberResponse memberResponse = review1Member1.get();
            public static final ReviewEventResponse eventResponse = EventData.response.reviewEvent.createValidReviewEventResponse();
            public static final String content = "리뷰 내용입니다. 리뷰 내용은 5자 이상 50자 이하입니다";
            public static final Pageable pageable = PageRequest.of(2, 20);
            public static final Long oldLastId = 30L;

            public static class _Relation {

                public static Member member() {
                    return MemberData.normal1.entity.get();
                }

                public static Event event() {
                    return EventData.event1.entity.get();
                }
            }

            public static class _Dto {

                public static class editRequest1 {

                    public static final Integer score = 5;
                    public static final String content = "수정된 리뷰 내용입니다. 리뷰 내용은 5자 이상 50자 이하입니다";
                }
            }
        }
    }

    public static class review1 {

        public static class entity {

            public static Review get() {
                Review ret = Review.builder()
                    .member(R.review1._Relation.member())
                    .event(R.review1._Relation.event())
                    .score(R.review1.score)
                    .content(R.review1.content)
                    .build();
                ReflectionTestUtils.setField(ret, "id", R.review1.id);
                return ret;
            }
        }

        public static class request {

            public static class create {

                public static ReviewCreateRequest get() {
                    return ReviewCreateRequest.builder()
                        .eventId(R.review1._Relation.event().getId())
                        .score(R.review1.score)
                        .content(R.review1.content)
                        .build();
                }
            }

            public static class edit1 {

                public static ReviewEditRequest get() {
                    return ReviewEditRequest.builder()
                        .score(editRequest1.score)
                        .content(editRequest1.content)
                        .build();
                }
            }
        }

        public static class response1 {

            public static ReviewResponse get() {
                return ReviewResponse.builder()
                    .id(R.review1.id)
                    .member(R.review1.memberResponse)
                    .event(R.review1.eventResponse)
                    .score(ReviewData.review1.request.create.get().getScore())
                    .content(ReviewData.review1.request.create.get().getContent())
                    .build();
            }
        }

        public static class response2 {

            public static ReviewResponse get() {
                return ReviewResponse.builder()
                    .id(R.review1.id)
                    .member(R.review1.memberResponse)
                    .event(R.review1.eventResponse)
                    .score(R.review1.score)
                    .content(ReviewData.review1.request.edit1.get().getContent())
                    .build();
            }
        }

        public static class reviewMemberResponse {

            public static ReviewMemberResponse get() {
                return ReviewMemberResponse.builder()
                    .memberId(normal1.id)
                    .nickname(normal1.nickname)
                    .build();
            }
        }

        public static class reviewEventResponse {

            public static ReviewEventResponse get() {
                return ReviewEventResponse.builder()
                    .eventId(event1.id)
                    .title(event1.title)
                    .startTime(event1.startTime)
                    .thumbnailUrl(event1.thumbnailUrl)
                    .build();
            }
        }
    }

    public static class sliceResponse {

        public static ReviewSliceResponse get() {
            return ReviewSliceResponse.builder()
                .reviews(List.of(
                    response1.get()))
                .sliceInfo(CommonData.sliceInfo.review.get())
                .build();
        }
    }

    public static class reviewSlice {

        public static Slice<Review> get() {
            return new SliceImpl<>(List.of(
                ReviewData.review1.entity.get()), R.review1.pageable, true);
        }
    }

    public static class reviewEventSliceResponse {

        public static ReviewEventSliceResponse get() {
            return ReviewEventSliceResponse.builder()
                .reviewEvents(List.of(EventData.response.reviewEvent.createValidReviewEventResponse()))
                .sliceInfo(CommonData.sliceInfo.event.get())
                .build();
        }
    }
}
