package org.ktc2.cokaen.wouldyouin.review;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.event1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.normal1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData.R.review1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData.review1.response1;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReviewEventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewResponse;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewSliceResponse;
import org.ktc2.cokaen.wouldyouin.review.application.ReviewService;
import org.ktc2.cokaen.wouldyouin.review.persist.ReviewRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class ReviewServiceUnitTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private EventService eventService;

    @Mock
    private MemberService memberService;

    @Mock
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewService(reviewRepository, eventService, memberService, reservationService);
    }

    @Test
    @DisplayName("리뷰 ID를 통해 해당 리뷰를 반환한다.")
    void getById() {
        // given
        given(reviewRepository.findById(review1.id)).willReturn(Optional.of(ReviewData.review1.entity.get()));

        // when
        ReviewResponse response = reviewService.getById(review1.id);

        // then
        assertThat(response).isEqualTo(response1.get());
    }

    @Test
    @DisplayName("멤버 ID를 통해 해당 멤버가 작성한 모든 리뷰를 반환한다.")
    void getAllByMemberId() {
        // given
        given(reviewRepository.findByMemberIdOrderByReviewIdDesc(normal1.id, review1.oldLastId, review1.pageable))
            .willReturn(ReviewData.reviewSlice.get());

        // when
        ReviewSliceResponse response = reviewService.getAllByMemberId(normal1.id, review1.pageable, review1.oldLastId);

        // then
        assertThat(response).isEqualTo(ReviewData.sliceResponse.get());
    }

    @Test
    @DisplayName("행사 ID를 통해 해당 행사의 모든 리뷰를 반환한다.")
    void getAllByEventId() {
        // given
        given(reviewRepository.findByEventIdOrderByReviewIdDesc(event1.id, review1.oldLastId, review1.pageable))
            .willReturn(ReviewData.reviewSlice.get());

        // when
        ReviewSliceResponse response = reviewService.getAllByEventId(event1.id, review1.pageable, review1.oldLastId);

        // then
        assertThat(response).isEqualTo(ReviewData.sliceResponse.get());
    }

    @Test
    @DisplayName("멤버 ID를 통해 아직 작성하지 않은 리뷰 목록을 반환한다.")
    void getUnreviewedEventsByMemberId() {
        // given
        given(reviewRepository.findUnreviewedEventsByMemberId(normal1.id, review1.oldLastId, review1.pageable))
            .willReturn(EventData.EventSlice.get());

        // when
        ReviewEventSliceResponse response =
            reviewService.getUnreviewedEventsByMemberId(normal1.id, review1.pageable, review1.oldLastId);

        // then
        assertThat(response).isEqualTo(ReviewData.reviewEventSliceResponse.get());
    }

    @Test
    @DisplayName("리뷰 생성 DTO를 통해 리뷰를 생성한다.")
    void create() {
        // given
        given(memberService.getByIdOrThrow(normal1.id)).willReturn(MemberData.normal1.entity.get());
        given(eventService.getByIdOrThrow(event1.id)).willReturn(EventData.event1.entity.get());
        given(reviewRepository.save(any())).willReturn(ReviewData.review1.entity.get());

        // when
        ReviewResponse response = reviewService.create(normal1.id, ReviewData.review1.request.create.get());

        // then
        assertThat(response).isEqualTo(response1.get());
    }

    @Test
    @DisplayName("리뷰 ID를 통해 해당 리뷰를 삭제한다.")
    void delete1() {
        // given
        given(reviewRepository.findById(review1.id)).willReturn(Optional.of(ReviewData.review1.entity.get()));

        // when
        reviewService.delete(normal1.id, review1.id);

        // then
        then(reviewRepository).should(times(1)).deleteById(review1.id);
    }

    @Test
    @DisplayName("리뷰 ID를 통해 해당 리뷰를 삭제한다.")
    void delete2() {
        // given
        Long unValidMemberId = 100L;
        given(reviewRepository.findById(review1.id)).willReturn(Optional.of(ReviewData.review1.entity.get()));

        // when
        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
            () -> reviewService.delete(unValidMemberId, review1.id));

        // then
        assertThat(exception.getMessage()).isEqualTo("member ID가 리뷰의 member ID와 일치하지 않습니다.");
    }
}
