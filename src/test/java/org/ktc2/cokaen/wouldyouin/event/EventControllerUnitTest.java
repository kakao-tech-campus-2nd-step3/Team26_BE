package org.ktc2.cokaen.wouldyouin.event;


import static org.ktc2.cokaen.wouldyouin._global.TestData.MemberDomain.createValidHost;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.EventController;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin._global.TestData.EventDomain;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockHost;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(EventController.class)
class EventControllerUnitTest {

    private static Long id;
    private static Host validHost;
    private static ObjectMapper objectMapper;
    @MockBean
    private EventService eventService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    private EventSliceResponse eventSliceResponse;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    public static void init() {
        id = 3L;
        validHost = createValidHost();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("모든 행사 조회 - 성공")
    @WithMockHost
    void getEventsByFilterOrderByDistanceAsc() throws Exception {
        // given
        LocationFilter locationFilter = new LocationFilter(0.0, 0.0, 10.0, 10.0);
        Location currentLocation = new Location(3.0, 2.0);
        Category category = Category.공예;
        Area area = Area.광주;
        int pageNumber = 1; // 교수님 
        int pageSize = 10; // 
        Long lastId = 1L;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        given(eventService.getAllByFilterOrderByDistanceAsc(
            locationFilter,
            currentLocation,
            category,
            area,
            pageable,
            lastId))
            .willReturn(eventSliceResponse);

        // when
        mockMvc.perform(get("/api/events")
                .param("startLatitude", locationFilter.getStartLatitude().toString())
                .param("startLongitude", locationFilter.getStartLongitude().toString())
                .param("endLatitude", locationFilter.getEndLatitude().toString())
                .param("endLongitude", locationFilter.getEndLongitude().toString())
                .param("latitude", currentLocation.getLatitude().toString())
                .param("longitude", currentLocation.getLongitude().toString())
                .param("category", category.toString())  // enum을 문자열로 변환하여 설정
                .param("area", area.toString())  // enum을 문자열로 변환하여 설정
                .param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize))
                .param("lastId", String.valueOf(lastId))
            ).andDo(print())
            .andExpect(status().isOk());

        // then
        then(eventService).should(times(1)).getAllByFilterOrderByDistanceAsc(
            any(LocationFilter.class),
            any(Location.class),
            eq(category),
            eq(area),
            eq(pageable),
            eq(lastId)
        );
    }

    @Test
    @DisplayName("주최자 id를 통한 모든 행사 조회 - 성공")
    @WithMockHost
    void getEventsByLocationByHostId() throws Exception {
        // given
        int pageNumber = 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long lastId = 1L;

        // when
        mockMvc.perform(get("/api/events/hosts/" + id)
                .param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize))
                .param("lastId", String.valueOf(lastId))
            )
            .andExpect(status().isOk());

        //then
        then(eventService).should(times(1)).getAllByHostIdOrderByCreatedDateDesc(
            eq(id),
            eq(pageable),
            eq(lastId)
        );
    }

    @Test
    @DisplayName("행사 id를 통한 행사 조회 - 성공")
    @WithMockHost
    void getEventByEventId() throws Exception {
        //given
        //when
        mockMvc.perform(get("/api/events/" + id))
            .andDo(print())
            .andExpect(status().isOk());

        //then
        then(eventService).should(times(1)).getById(eq(id));
    }

    @Test
    @DisplayName("행사 생성 - 성공")
    @WithMockHost
    void createEvent() throws Exception {
        //given
        //when
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EventDomain.createValidEventCreateRequest())))
            .andDo(print())
            .andExpect(status().isCreated());

        //then
        then(eventService).should(times(1)).create(eq(id), any(EventCreateRequest.class));
    }

    @Test
    @DisplayName("행사 수정 - 성공")
    @WithMockHost
    void updateEvent() throws Exception {
        //given
        //when
        mockMvc.perform(put("/api/events/" + id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EventDomain.createValidEventCreateRequest())))
            .andExpect(status().isOk());

        //then
        then(eventService).should(times(1)).update(eq(id), eq(id), any(EventEditRequest.class));
    }

    @Test
    @DisplayName("행사 삭제 - 성공")
    @WithMockHost
    void deleteEvent() throws Exception {
        //given
        //when
        mockMvc.perform(delete("/api/events/" + id)
            .with(csrf())
        ).andExpect(status().isNoContent());

        //then
        then(eventService).should(times(1)).delete(eq(id), eq(id));
    }
}