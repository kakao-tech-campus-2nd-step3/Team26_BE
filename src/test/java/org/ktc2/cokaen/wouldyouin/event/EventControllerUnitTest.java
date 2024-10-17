package org.ktc2.cokaen.wouldyouin.event;


import static java.lang.Math.abs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin.event.api.EventController;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.global.TestData;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EventController.class)
class EventControllerUnitTest {

    @MockBean
    private EventService eventService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    private static Long id;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        id = abs(new Random().nextLong());
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("모든 행사 조회 - 성공")
    void getEvents() throws Exception {
        mockMvc.perform(get("/api/events")).andExpect(status().isOk());
        verify(eventService).getAll();
    }

    @Test
    @DisplayName("주최자 id를 통한 모든 행사 조회 - 성공")
    void getEventsByHostId() throws Exception {
        mockMvc.perform(get("/api/events/hosts/" + id)).andExpect(status().isOk());
        verify(eventService).getAllByHostId(id);

    }

    @Test
    @DisplayName("행사 id를 통한 행사 조회 - 성공")
    void getEventByEventId() throws Exception {
        mockMvc.perform(get("/api/events/" + id)).andExpect(status().isOk());
        verify(eventService).getById(id);
    }

    @Test
    @DisplayName("행사 생성 - 성공")
    void createEvent() throws Exception {
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestData.validEventCreateRequest)))
            .andExpect(status().isCreated());
        verify(eventService).create(any(EventCreateRequest.class));
    }

    @Test
    @DisplayName("행사 수정 - 성공")
    void updateEvent() throws Exception {
        mockMvc.perform(put("/api/events/" + id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestData.validEventCreateRequest)))
            .andExpect(status().isOk());
        verify(eventService).update(eq(id), any(EventEditRequest.class));
    }

    @Test
    @DisplayName("행사 삭제 - 성공")
    void deleteEvent() throws Exception {
        mockMvc.perform(delete("/api/events/" + id)).andExpect(status().isNoContent());
        verify(eventService).delete(id);
    }
}