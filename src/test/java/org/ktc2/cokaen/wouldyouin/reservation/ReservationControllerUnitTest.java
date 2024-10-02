package org.ktc2.cokaen.wouldyouin.reservation;

import static java.lang.Math.abs;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.reservation.api.ReservationController;
import org.ktc2.cokaen.wouldyouin.global.TestData;
import org.ktc2.cokaen.wouldyouin._common.util.JwtManager;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReservationController.class)
class ReservationControllerUnitTest {

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtManager jwtManager;

    @Autowired
    private MockMvc mockMvc;

    private static Long id;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void init() {
        id = abs(new Random().nextLong());
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("모든 예약 조회 - 성공")
    void getReservations() throws Exception {
        mockMvc.perform(get("/api/reservations")).andExpect(status().isOk());
        verify(reservationService).getAll();
    }

    @Test
    @DisplayName("예약 조회 - 성공")
    void getReservation() throws Exception {
        mockMvc.perform(get("/api/reservations/" + id)).andExpect(status().isOk());
        verify(reservationService).getById(id);
    }

    @Test
    @DisplayName("사용자 id를 통한 예약 조회 - 성공")
    void getReservationsByMemberId() throws Exception {
        mockMvc.perform(get("/api/reservations/members/" + id)).andExpect(status().isOk());
        verify(reservationService).getAllByMemberId(id);

    }

    @Test
    @DisplayName("행사 id를 통한 예약 조회 - 성공")
    void getReservationsByEventId() throws Exception {
        mockMvc.perform(get("/api/reservations/events/" + id)).andExpect(status().isOk());
        verify(reservationService).getAllByEventId(id);
    }

    @Test
    @DisplayName("예약 생성 - 성공")
    void createReservation() throws Exception {
        mockMvc.perform(post("/api/reservations").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestData.validReservationRequest)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("예약 삭제 - 성공")
    void deleteReservation() throws Exception {
        mockMvc.perform(delete("/api/reservations/" + id)).andExpect(status().isNoContent());
        verify(reservationService).delete(id);
    }
}