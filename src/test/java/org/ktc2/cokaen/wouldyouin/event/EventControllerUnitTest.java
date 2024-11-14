package org.ktc2.cokaen.wouldyouin.event;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import java.time.LocalDateTime;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockCurator1;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockHost1;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockMember1;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.host1;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.event.api.EventController;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationRequest;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(EventController.class)
class EventControllerUnitTest {

    private final Long randomId = abs(new Random().nextLong());
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private EventService eventService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("RequestParam을 통해 전달받은 위치를 기반으로 이벤트 목록을 조회한다.")
    @WithMockMember1
    void getEventsByFilterOrderByDistanceAsc() throws Exception {
        // given, when
        mockMvc.perform(get("/api/events/filter")
                .param("startLatitude", "0.0")
                .param("startLongitude", "0.0")
                .param("endLatitude", "10.0")
                .param("endLongitude", "10.0")
                .param("latitude", "3.0")
                .param("longitude", "2.0")
                .param("title", "testTitle")
                .param("category", Category.밴드.name())
                .param("area", Area.광주.name())
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(eventService).should(times(1)).getAllByFilterOrderByDistanceAsc(
            any(LocationFilter.class),
            any(LocationRequest.class),
            eq("testTitle"),
            eq(Category.밴드),
            eq(Area.광주),
            eq(PageRequest.of(5, 20)), eq(100L)
        );
    }

    @Test
    @DisplayName("모든 이벤트 목록을 조회한다.")
    @WithMockMember1
    void getEvents1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/events")
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(eventService).should(times(1)).getAllByCreatedDateDesc(
            eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam을 통해 페이지 정보를 지정하지 않은 경우, 디폴트 값으로 해당하는 호스트의 이벤트 목록을 조회한다.")
    @WithMockMember1
    void getEvents2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/events")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(eventService).should(times(1)).getAllByCreatedDateDesc(
            eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("호스트 ID를 통해 해당하는 호스트의 이벤트 목록을 조회한다.")
    @WithMockMember1
    void getEventsByHostId() throws Exception {
        // given, when
        mockMvc.perform(get("/api/events/hosts/" + randomId)
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(eventService).should(times(1)).getAllByHostIdOrderByCreatedDateDesc(
            eq(randomId), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("이벤트 ID를 통해 해당하는 이벤트를 조회한다.")
    @WithMockMember1
    void getEventByEventId() throws Exception {
        // given, when
        mockMvc.perform(get("/api/events/" + randomId)).andDo(print())
            .andExpect(status().isOk());

        // then
        then(eventService).should(times(1)).getById(eq(randomId));
    }

    @Test
    @DisplayName("RequestBody로 전달받은 정보를 통해 이벤트를 생성한다.")
    @WithMockHost1
    void createEvent1() throws Exception {
        // given
        ArgumentCaptor<EventCreateRequest> captor = ArgumentCaptor.forClass(
            EventCreateRequest.class);
        EventCreateRequest request = EventData.event1.request.create.get();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(eventService).should(times(1)).create(eq(host1.memberIdentifier), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Curator의 권한으로는 이벤트를 생성할 수 없다.")
    @WithMockCurator1
    void createEvent2() throws Exception {
        // given, when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EventData.event1.request.create.get())))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Member의 권한으로는 이벤트를 생성할 수 없다.")
    @WithMockMember1
    void createEvent3() throws Exception {
        // given, when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EventData.event1.request.create.get())))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 제목에는 빈 값이 들어갈 수 없다.")
    @WithMockHost1
    void createEvent4() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .title("").build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("제목은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 내용에는 빈 값이 들어갈 수 없다.")
    @WithMockHost1
    void createEvent5() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .content(null).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 내용은 20자 이상 1000자 이하이어야 한다.")
    @WithMockHost1
    void createEvent6() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .content("짧은 내용").build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 20자 이상 1000자 이하입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 지역은 필수 입력값이다.")
    @WithMockHost1
    void createEvent7() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .area(null).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("지역는 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 장소는 필수 입력값이다.")
    @WithMockHost1
    void createEvent8() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .location(null).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("장소는 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 시작 시간은 필수 입력값이다.")
    @WithMockHost1
    void createEvent9() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .startTime(null).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("시작시간은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 종료 시간은 필수 입력값이다.")
    @WithMockHost1
    void createEvent10() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .endTime(null).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("종료시간은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 시작 시간은 현재 또는 이후 시간이어야 한다.")
    @WithMockHost1
    void createEvent11() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .startTime(LocalDateTime.now().minusDays(1)).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("시작 시간은 현재 시간 이후여야 합니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 종료 시간은 현재 또는 이후 시간이어야 한다.")
    @WithMockHost1
    void createEvent12() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .startTime(LocalDateTime.now().minusDays(2))  // startTime을 미래로 설정
            .endTime(LocalDateTime.now().minusDays(1))   // endTime을 과거로 설정
            .build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(
                org.hamcrest.Matchers.containsString("종료 시간은 현재 시간 이후여야 합니다.")));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 종료 시간은 시작 시간 이후 시간이어야 한다.")
    @WithMockHost1
    void createEvent13() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .endTime(LocalDateTime.now().plusDays(2)).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("종료 시간은 시작 시간 이후여야 합니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 가격은 0원 이상이어야 한다.")
    @WithMockHost1
    void createEvent14() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .price(-1).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("가격은 0원 이상입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 가격은 1,000,000원 이하이어야 한다.")
    @WithMockHost1
    void createEvent15() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .price(1000001).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("가격은 1,000,000원 이하입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 총 좌석은 0석 이상이어야 한다.")
    @WithMockHost1
    void createEvent16() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .totalSeat(-1).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("총 좌석은 0석 이상입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 총 좌석은 1,000석 이하이어야 한다.")
    @WithMockHost1
    void createEvent17() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .totalSeat(1001).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("총 좌석은 1,000석 이하입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 생성 시, 카테고리는 필수 입력값이다.")
    @WithMockHost1
    void createEvent18() throws Exception {
        // given
        EventCreateRequest request = EventData.event1.request.create.get().toBuilder()
            .category(null).build();

        // when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("카테고리는 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("RequestBody로 전달받은 정보를 통해 이벤트를 수정한다.")
    @WithMockHost1
    void updateEvent1() throws Exception {
        // given
        ArgumentCaptor<EventEditRequest> captor = ArgumentCaptor.forClass(EventEditRequest.class);
        EventEditRequest request = EventData.event1.request.edit1.get();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(eventService).should(times(1))
            .update(eq(host1.memberIdentifier), eq(randomId), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("Curator의 권한으로는 이벤트를 수정할 수 없다.")
    @WithMockCurator1
    void updateEvent2() throws Exception {
        // given, when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EventData.event1.request.edit1.get())))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Member의 권한으로는 이벤트를 수정할 수 없다.")
    @WithMockMember1
    void updateEvent3() throws Exception {
        // given, when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EventData.event1.request.edit1.get())))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 제목에는 빈 값이 들어갈 수 없다.")
    @WithMockMember1
    void updateEvent4() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder().title(null)
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("제목은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 내용은 빈 값이 들어갈 수 없다.")
    @WithMockMember1
    void updateEvent5() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder().content(null)
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 내용은 20자 이상 1000자 이하이어야 한다.")
    @WithMockMember1
    void updateEvent6() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder().content("짧은 내용")
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 20자 이상 1000자 이하입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 지역은 필수이다.")
    @WithMockMember1
    void updateEvent7() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder().area(null)
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("지역는 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 장소는 필수이다.")
    @WithMockMember1
    void updateEvent8() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder().location(null)
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("장소는 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 시작 시간은 필수이다")
    @WithMockMember1
    void updateEvent9() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder()
            .startTime(null).build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("시작 시간은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 시작 시간은 현재 시간 이후여야 한다.")
    @WithMockMember1
    void updateEvent10() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder()
            .startTime(LocalDateTime.now().minusDays(1))
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("시작 시간은 현재 시간 이후여야 합니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 종료 시간은 필수이다")
    @WithMockMember1
    void updateEvent11() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder()
            .endTime(null).build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("종료 시간은 필수입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 종료 시간은 현재 시간 이후여야 한다.")
    @WithMockMember1
    void updateEvent12() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder()
            .endTime(LocalDateTime.now().minusDays(1))
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(
                org.hamcrest.Matchers.containsString("종료 시간은 현재 시간 이후여야 합니다.")));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 종료 시간은 시작 시간 이후여야 한다.")
    @WithMockMember1
    void updateEvent13() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder()
            .endTime(LocalDateTime.now().plusDays(2))
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("종료 시간은 시작 시간 이후여야 합니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 가격은 0원 이상 1,000,000원 이하이어야 한다.")
    @WithMockMember1
    void updateEvent14() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder()
            .price(1000001) // 1,000,001원 설정
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("가격은 1,000,000원 이하입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("이벤트 수정 시, 총 좌석은 0석 이상 1,000석 이하이어야 한다.")
    @WithMockMember1
    void updateEvent15() throws Exception {
        // given
        EventEditRequest request = EventData.event1.request.edit1.get().toBuilder()
            .totalSeat(1001) // 1,001석 설정
            .build();

        // when
        mockMvc.perform(put("/api/events/" + randomId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("총 좌석은 1,000석 이하입니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("PathVariable로 전달받은 이벤트 ID에 해당하는 이벤트를 삭제한다.")
    @WithMockHost1
    void deleteEvent() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/events/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isNoContent());

        // then
        then(eventService).should(times(1)).delete(eq(host1.memberIdentifier), eq(randomId));
    }

    @Test
    @DisplayName("Curator의 권한으로는 큐레이션을 삭제할 수 없다.")
    @WithMockCurator1
    void deleteEvent2() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/events/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Member의 권한으로는 큐레이션을 삭제할 수 없다.")
    @WithMockMember1
    void deleteEvent3() throws Exception {
        // given, when
        mockMvc.perform(delete("/api/events/" + randomId)
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(eventService).shouldHaveNoInteractions();
    }
}