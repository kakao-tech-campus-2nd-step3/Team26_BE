package org.ktc2.cokaen.wouldyouin.review;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockCurator1;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockHost1;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockMember1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.normal1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.review.api.ReviewController;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewCreateRequest;
import org.ktc2.cokaen.wouldyouin.review.api.dto.ReviewEditRequest;
import org.ktc2.cokaen.wouldyouin.review.application.ReviewService;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(ReviewController.class)
public class ReviewControllerUnitTest {

    private static final long randomId = abs(new Random().nextLong());
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ReviewService reviewService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("jwt 토큰 정보에 해당하는 member의 리뷰 목록을 조회한다.")
    @WithMockMember1
    void getReviewsByMemberId1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reviews")
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1)).getAllByMemberId(
            eq(normal1.id), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam으로 페이지 정보가 주어지지 않으면 기본값으로 리뷰 목록을 조회한다.")
    @WithMockMember1
    void getReviewsByMemberId2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reviews")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1)).getAllByMemberId(
            eq(normal1.id), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("PathVariable로 이벤트 ID를 받아 해당하는 리뷰 목록을 조회한다.")
    @WithMockMember1
    void getReviewsByEventId() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reviews/events/" + randomId)
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1)).getAllByEventId(
            eq(randomId), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam으로 페이지 정보가 주어지지 않으면 기본값으로 리뷰 목록을 조회한다.")
    @WithMockMember1
    void getReviewsByEventId2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reviews/events/" + randomId))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1)).getAllByEventId(
            eq(randomId), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("리뷰 ID를 통해 리뷰를 조회한다.")
    @WithMockMember1
    void getReviewByReviewId() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reviews/" + randomId)).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1)).getById(randomId);
    }

    @Test
    @DisplayName("jwt 토큰 정보에 해당하는 member가 리뷰를 작성하지 않은 이벤트 목록을 조회한다.")
    @WithMockMember1
    void getUnreviewedEventsByMemberId1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reviews/events")
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1)).getUnreviewedEventsByMemberId(
            eq(normal1.id), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("Host의 권한으로 자신이 리뷰를 작성하지 않은 이벤트 목록을 조회할 수 없다.")
    @WithMockHost1
    void getUnreviewedEventsByMemberId2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reviews/events")
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Member 권한으로 RequestBody를 통해 리뷰를 생성한다.")
    @WithMockMember1
    void createReview1() throws Exception {
        // given
        ArgumentCaptor<ReviewCreateRequest> captor = ArgumentCaptor.forClass(
            ReviewCreateRequest.class);
        ReviewCreateRequest request = ReviewData.review1.request.create.get();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(reviewService).should(times(1)).create(eq(normal1.id), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Curator 권한으로 RequestBody를 통해 리뷰를 생성한다.")
    @WithMockCurator1
    void createReview2() throws Exception {
        // given
        ArgumentCaptor<ReviewCreateRequest> captor = ArgumentCaptor.forClass(
            ReviewCreateRequest.class);
        ReviewCreateRequest request = ReviewData.review1.request.create.get();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(reviewService).should(times(1)).create(eq(curator1.id), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Host 권한으로 RequestBody를 통해 리뷰를 생성할 수 없다.")
    @WithMockHost1
    void createReview3() throws Exception {
        // given
        ArgumentCaptor<ReviewCreateRequest> captor = ArgumentCaptor.forClass(
            ReviewCreateRequest.class);
        ReviewCreateRequest request = ReviewData.review1.request.create.get();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 생성 시 이벤트 ID는 빈 값이 될 수 없다.")
    @WithMockMember1
    void createReview4() throws Exception {
        // given
        ReviewCreateRequest request = ReviewData.review1.request.create.get().toBuilder()
            .eventId(null).build();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("이벤트 ID는 필수입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 생성 시 별점은 빈 값이 될 수 없다.")
    @WithMockMember1
    void createReview5() throws Exception {
        // given
        ReviewCreateRequest request = ReviewData.review1.request.create.get().toBuilder()
            .score(null).build();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("별점은 필수입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 생성 시 별점은 0점 이상이어야 한다.")
    @WithMockMember1
    void createReview6() throws Exception {
        // given
        ReviewCreateRequest request = ReviewData.review1.request.create.get().toBuilder()
            .score(-1).build();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("별점은 0점 이상입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 생성 시 별점은 5점 이하이어야 한다.")
    @WithMockMember1
    void createReview7() throws Exception {
        // given
        ReviewCreateRequest request = ReviewData.review1.request.create.get().toBuilder()
            .score(8).build();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("별점은 5점 이하입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 생성 시 내용은 빈 값이 될 수 없다.")
    @WithMockMember1
    void createReview8() throws Exception {
        // given
        ReviewCreateRequest request = ReviewData.review1.request.create.get().toBuilder()
            .content(null).build();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 필수입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 생성 시 내용은 5자 이상 50자 이하입니다.")
    @WithMockMember1
    void createReview9() throws Exception {
        // given
        ReviewCreateRequest request = ReviewData.review1.request.create.get().toBuilder()
            .content("짧은내용").build();

        // when
        mockMvc.perform(post("/api/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 5자 이상 50자 이하입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Member 권한으로 RequestBody를 통해 리뷰를 수정한다.")
    @WithMockMember1
    void updateReview1() throws Exception {
        // given
        ArgumentCaptor<ReviewEditRequest> captor = ArgumentCaptor.forClass(
            ReviewEditRequest.class);
        ReviewEditRequest request = ReviewData.review1.request.edit1.get();

        // when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1)).update(eq(normal1.id), eq(randomId), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Curator 권한으로 RequestBody를 통해 리뷰를 생성한다.")
    @WithMockCurator1
    void updateReview2() throws Exception {
        // given
        ArgumentCaptor<ReviewEditRequest> captor = ArgumentCaptor.forClass(
            ReviewEditRequest.class);
        ReviewEditRequest request = ReviewData.review1.request.edit1.get();

        // when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(reviewService).should(times(1))
            .update(eq(curator1.id), eq(randomId), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Host 권한으로 RequestBody를 통해 리뷰를 생성할 수 없다.")
    @WithMockHost1
    void updateReview3() throws Exception {
        // given, when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ReviewData.review1.request.edit1.get())))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 수정 시, 별점은 빈 값이 될 수 없다. ")
    @WithMockCurator1
    void updateReview4() throws Exception {
        // given
        ReviewEditRequest request = ReviewData.review1.request.edit1.get().toBuilder()
            .score(null).build();

        // when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("별점은 필수입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 수정 시, 별점은 0점 이상이어야 한다.")
    @WithMockCurator1
    void updateReview5() throws Exception {
        // given
        ReviewEditRequest request = ReviewData.review1.request.edit1.get().toBuilder()
            .score(-1).build();

        // when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("별점은 0점 이상입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 수정 시, 별점은 5점 이하여야 한다.")
    @WithMockCurator1
    void updateReview6() throws Exception {
        // given
        ReviewEditRequest request = ReviewData.review1.request.edit1.get().toBuilder()
            .score(8).build();

        // when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("별점은 5점 이하입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 수정 시, 내용은 필수입니다.")
    @WithMockCurator1
    void updateReview7() throws Exception {
        // given
        ReviewEditRequest request = ReviewData.review1.request.edit1.get().toBuilder()
            .content(null).build();

        // when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 필수입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("리뷰 수정 시, 내용은 5자 이상 50자 이하입니다.")
    @WithMockCurator1
    void updateReview8() throws Exception {
        // given
        ReviewEditRequest request = ReviewData.review1.request.edit1.get().toBuilder()
            .content("짧은내용").build();

        // when
        mockMvc.perform(put("/api/reviews/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 5자 이상 50자 이하입니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Member 권한으로 리뷰 ID를 통해 리뷰를 삭제한다.")
    @WithMockMember1
    void deleteReview1() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/reviews/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isNoContent());

        // then
        then(reviewService).should(times(1)).delete(normal1.id, randomId);
    }

    @Test
    @DisplayName("Curator 권한으로 리뷰 ID를 통해 리뷰를 삭제한다.")
    @WithMockCurator1
    void deleteReview2() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/reviews/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isNoContent());

        // then
        then(reviewService).should(times(1)).delete(curator1.id, randomId);
    }

    @Test
    @DisplayName("Host 권한으로 리뷰 ID를 통해 리뷰를 삭제할 수 없다.")
    @WithMockHost1
    void deleteReview3() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/reviews/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reviewService).shouldHaveNoInteractions();
    }
}
