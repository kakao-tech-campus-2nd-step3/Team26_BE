package org.ktc2.cokaen.wouldyouin.reservation;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.validCuratorId;
import static org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.validHostId;
import static org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.validMemberId;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin._global.TestData.ReservationDomain;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockCurator;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockHost;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockMember;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.reservation.api.ReservationController;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(ReservationController.class)
class ReservationControllerUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    private static final long randomId = abs(new Random().nextLong());

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("jwt 토큰 정보에 해당하는 member의 예약 목록을 조회한다.")
    @WithMockMember
    void getReservationsByMemberId1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations")
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reservationService).should(times(1)).getAllByMemberId(
            eq(validMemberId), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam으로 페이지 정보가 주어지지 않으면 기본값으로 예약을 조회한다.")
    @WithMockMember
    void getReservationsByMemberId2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reservationService).should(times(1)).getAllByMemberId(
            eq(validMemberId), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("jwt 토큰 정보에 해당하는 curator의 예약 목록을 조회한다.")
    @WithMockCurator
    void getReservationsByMemberId3() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations")
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reservationService).should(times(1)).getAllByMemberId(
            eq(validCuratorId), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("Host의 권한으로 자신의 예약을 조회할 수 없다.")
    @WithMockHost
    void getReservationsByMemberId4() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations")
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("PathVariable로 이벤트 ID를 받아 해당하는 이벤트를 조회한다.")
    @WithMockHost
    void getReservationByEventId1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations/events/" + randomId)
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(reservationService).should(times(1)).getAllByEventId(
            eq(validHostId), eq(randomId), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam으로 페이지 정보가 주어지지 않으면 기본값으로 예약을 조회한다.")
    @WithMockHost
    void getReservationByEventId2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations/events/" + randomId))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(reservationService).should(times(1)).getAllByEventId(
            eq(validHostId), eq(randomId), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("Member의 권한으로 이벤트 ID를 통해 이벤트의 예약을 조회할 수 없다.")
    @WithMockMember
    void getReservationByEventId3() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations/events/" + randomId))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Curator의 권한으로 이벤트 ID를 통해 이벤트의 예약을 조회할 수 없다.")
    @WithMockCurator
    void getReservationByEventId4() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations/events/" + randomId))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("예약 ID를 통해 예약을 조회한다.")
    @WithMockCurator
    void getReservationById1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/reservations/" + randomId))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(reservationService).should(times(1)).getById(randomId);
    }

    @Test
    @DisplayName("Member 권한으로 RequestBody를 통해 예약을 생성한다.")
    @WithMockMember
    void createReservation1() throws Exception {
        // given
        ArgumentCaptor<ReservationRequest> captor = ArgumentCaptor.forClass(ReservationRequest.class);
        ReservationRequest request = ReservationDomain.createValidReservationRequest();

        // when
        mockMvc.perform(post("/api/reservations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(reservationService).should(times(1)).create(eq(validMemberId), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Curator 권한으로 RequestBody를 통해 예약을 생성한다.")
    @WithMockCurator
    void createReservation2() throws Exception {
        // given
        ArgumentCaptor<ReservationRequest> captor = ArgumentCaptor.forClass(ReservationRequest.class);
        ReservationRequest request = ReservationDomain.createValidReservationRequest();

        // when
        mockMvc.perform(post("/api/reservations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(reservationService).should(times(1)).create(eq(validCuratorId), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Host 권한으로 예약을 생성할 수 없다.")
    @WithMockHost
    void createReservation3() throws Exception {
        // given, when
        mockMvc.perform(post("/api/reservations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ReservationDomain.createValidReservationRequest())))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("예약 생성 시 이벤트 ID는 빈 값이 될 수 없다.")
    @WithMockMember
    void createReservation4() throws Exception {
        // given
        ReservationRequest request = ReservationDomain.createValidReservationRequest().toBuilder()
            .eventId(null).build();

        // when
        mockMvc.perform(post("/api/reservations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("이벤트 ID는 필수입니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("예약 생성 시 수량은 빈 값이 될 수 없다.")
    @WithMockMember
    void createReservation5() throws Exception {
        // given
        ReservationRequest request = ReservationDomain.createValidReservationRequest().toBuilder()
            .quantity(null).build();

        // when
        mockMvc.perform(post("/api/reservations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("수량은 필수입니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("예약 생성 시 수량은 1개 이상이어야 한다.")
    @WithMockMember
    void createReservation6() throws Exception {
        // given
        ReservationRequest request = ReservationDomain.createValidReservationRequest().toBuilder()
            .quantity(0).build();

        // when
        mockMvc.perform(post("/api/reservations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("수량은 1개 이상이어야 합니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Member 권한으로 예약 ID를 통해 예약을 삭제한다.")
    @WithMockMember
    void deleteReservation1() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/reservations/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isNoContent());

        // then
        then(reservationService).should(times(1)).delete(validMemberId, randomId);
    }

    @Test
    @DisplayName("Curator 권한으로 예약 ID를 통해 예약을 삭제한다.")
    @WithMockCurator
    void deleteReservation2() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/reservations/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isNoContent());

        // then
        then(reservationService).should(times(1)).delete(validCuratorId, randomId);
    }

    @Test
    @DisplayName("Host 권한으로 예약 ID를 통해 예약을 삭제한다.")
    @WithMockHost
    void deleteReservation3() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/reservations/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(reservationService).shouldHaveNoInteractions();
    }
}