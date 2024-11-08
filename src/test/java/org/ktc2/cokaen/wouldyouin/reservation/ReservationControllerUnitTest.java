//package org.ktc2.cokaen.wouldyouin.reservation;
//
//import static java.lang.Math.abs;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.times;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import java.util.Random;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.ktc2.cokaen.wouldyouin._global.TestData.MemberDomain;
//import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockCurator;
//import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockHost;
//import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockMember;
//import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
//import org.ktc2.cokaen.wouldyouin.reservation.api.ReservationController;
//import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@WebMvcTest(ReservationController.class)
//class ReservationControllerUnitTest {
//
//    private static ObjectMapper objectMapper;
//
//    @MockBean
//    private ReservationService reservationService;
//
//    @MockBean
//    private JwtAuthFilter jwtAuthFilter;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//    private static final long randomId = abs(new Random().nextLong());
//
//    @BeforeAll
//    public static void init() {
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//    }
//
//    @BeforeEach
//    public void setup() throws Exception {
//        mockMvc = MockMvcBuilders
//            .webAppContextSetup(context)
//            .apply(springSecurity())
//            .build();
//    }
//
//    @Test
//    @DisplayName("jwt 토큰 정보에 해당하는 member의 예약 목록을 조회한다.")
//    @WithMockMember
//    void getReservationsByMemberId1() throws Exception {
//        // given, when
//        mockMvc.perform(get("/api/reservations")
//                .param("page", "5")
//                .param("size", "20")
//                .param("lastId", "100")).andDo(print())
//            .andExpect(status().isOk());
//
//        // then
//        then(reservationService).should(times(1)).getAllByMemberId(
//            eq(MemberDomain.validMemberId), eq(PageRequest.of(5, 20)), eq(100L));
//    }
//
//    @Test
//    @DisplayName("RequestParam으로 페이지 정보가 주어지지 않으면 기본값으로 페이지를 생성한다")
//    @WithMockMember
//    void getReservationsByMemberId2() throws Exception {
//        // given, when
//        mockMvc.perform(get("/api/reservations")).andDo(print())
//            .andExpect(status().isOk());
//
//        // then
//        then(reservationService).should(times(1)).getAllByMemberId(
//            eq(MemberDomain.validMemberId), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
//    }
//
//    @Test
//    @DisplayName("jwt 토큰 정보에 해당하는 curator의 예약 목록을 조회한다.")
//    @WithMockCurator
//    void getReservationsByMemberId3() throws Exception {
//        // given, when
//        mockMvc.perform(get("/api/reservations")
//                .param("page", "5")
//                .param("size", "20")
//                .param("lastId", "100")).andDo(print())
//            .andExpect(status().isOk());
//
//        // then
//        then(reservationService).should(times(1)).getAllByMemberId(
//            eq(MemberDomain.validMemberId), eq(PageRequest.of(5, 20)), eq(100L));
//    }
//
//    @Test
//    @DisplayName("Host의 권한으로 자신의 예약을 조회할 수 없다.")
//    @WithMockHost
//    void getReservationsByMemberId4() throws Exception {
//        // given, when
//        mockMvc.perform(get("/api/reservations")
//                .param("page", "5")
//                .param("size", "20")
//                .param("lastId", "100")).andDo(print())
//            .andExpect(status().isUnauthorized());
//
//        // then
//        then(reservationService).shouldHaveNoInteractions();
//    }
//
//    @Test
//    @DisplayName("PathVariable로 이벤트 ID를 받아 해당하는 이벤트를 조회한다.")
//    @WithMockHost
//    void getReservationsByMemberId4() throws Exception {
//        // given, when
//        mockMvc.perform(get("/api/reservations")
//                .param("page", "5")
//                .param("size", "20")
//                .param("lastId", "100")).andDo(print())
//            .andExpect(status().isUnauthorized());
//
//        // then
//        then(reservationService).shouldHaveNoInteractions();
//    }
//}