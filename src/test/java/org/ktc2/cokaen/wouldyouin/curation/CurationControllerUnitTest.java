package org.ktc2.cokaen.wouldyouin.curation;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.curation.api.CurationController;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationService;
import org.ktc2.cokaen.wouldyouin.global.TestData.CurationDomain;
import org.ktc2.cokaen.wouldyouin.global.TestData.MemberDomain;
import org.ktc2.cokaen.wouldyouin.global.mockMember.WithMockCurator;
import org.ktc2.cokaen.wouldyouin.global.mockMember.WithMockHost;
import org.ktc2.cokaen.wouldyouin.global.mockMember.WithMockMember;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(CurationController.class)
class CurationControllerUnitTest {

    private static ObjectMapper objectMapper;

    @MockBean
    private CurationService curationService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    private static final long randomId = abs(new Random().nextLong());


    @BeforeAll
    public static void init() {
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
    @DisplayName("RequestParam을 통해 전달받은 지역의 큐레이션 목록을 조회한다.")
    @WithMockMember
    void getCurationsByAreaOrderByCreatedDateDesc1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/curations")
                .param("area", Area.광주.name())
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getAllByAreaOrderByCreatedDateDesc(
            eq(Area.광주), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam을 통해 지역을 지정하지 않은 경우, 전체 지역의 큐레이션 목록을 조회한다.")
    @WithMockMember
    void getCurationsByAreaOrderByCreatedDateDesc2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/curations")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getAllByAreaOrderByCreatedDateDesc(
            eq(Area.전체), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("ReqeustParam을 통해 요청할 페이지에 대한 정보를 전달받아, 해당하는 호스트의 큐레이션 목록을 조회한다.")
    @WithMockMember
    void getCurationsByCuratorIdOrderByCreatedDateDesc1() throws Exception {
        // given, when
        mockMvc.perform(get("/api/curations/curators/" + randomId)
                .param("page", "5")
                .param("size", "20")
                .param("lastId", "100")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getAllByCuratorIdOrderByCreatedDateDesc(
            eq(randomId), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam을 통해 페이지 정보를 지정하지 않은 경우, 디폴트 값으로 해당하는 호스트의 큐레이션 목록을 조회한다.")
    @WithMockMember
    void getCurationsByCuratorIdOrderByCreatedDateDesc2() throws Exception {
        // given, when
        mockMvc.perform(get("/api/curations/curators/" + randomId)).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getAllByCuratorIdOrderByCreatedDateDesc(
            eq(randomId), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("큐레이션 ID를 통해 해당하는 큐레이션을 조회한다.")
    @WithMockMember
    void getCurationByCurationId() throws Exception {
        // given, when
        mockMvc.perform(get("/api/curations/" + randomId)).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getById(eq(randomId));
    }

    @Test
    @DisplayName("리퀘스트 바디로 전달받은 정보를 통해 큐레이션을 생성한다.")
    @WithMockCurator
    void createCuration1() throws Exception {
        // given
        ArgumentCaptor<CurationCreateRequest> captor = ArgumentCaptor.forClass(CurationCreateRequest.class);
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(curationService).should(times(1)).create(eq(MemberDomain.curatorId), captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    @DisplayName("호스트의 권한으로는 큐레이션을 생성할 수 없다.")
    @WithMockHost
    void createCuration2() throws Exception {
        // given, when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CurationDomain.createValidCurationCreateRequest())))
            .andDo(print())
            .andExpect(status().isUnauthorized());

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("멤버의 권한으로는 큐레이션을 생성할 수 없다.")
    @WithMockMember
    void createCuration3() throws Exception {
        // given, when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CurationDomain.createValidCurationCreateRequest())))
            .andDo(print())
            .andExpect(status().isUnauthorized());

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 제목에는 빈 값이 들어갈 수 없다.")
    @WithMockCurator
    void createCuration4() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder()
            .title("").build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("제목은 필수입니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 큐레이션 카드의 부제목에는 빈 값이 들어갈 수 없다.")
    @WithMockCurator
    void createCuration5() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder()
            .curationCards(List.of(CurationDomain.createValidCurationCardRequest1().toBuilder().subtitle("").build()))
            .build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("부제목은 필수입니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 큐레이션 카드의 내용에는 빈 값이 들어갈 수 없다.")
    @WithMockCurator
    void createCuration6() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder()
            .curationCards(List.of(CurationDomain.createValidCurationCardRequest1().toBuilder().content(null).build()))
            .build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 필수입니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 큐레이션 카드의 내용에는 빈 값이 들어갈 수 없다.")
    @WithMockCurator
    void createCuration7() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder()
            .curationCards(List.of(CurationDomain.createValidCurationCardRequest1().toBuilder().content(null).build()))
            .build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 필수입니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 큐레이션 카드의 내용은 20자 이상 1000자 이하이어야 한다.")
    @WithMockCurator
    void createCuration8() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder()
            .curationCards(List.of(CurationDomain.createValidCurationCardRequest1().toBuilder().content("짧은 내용").build()))
            .build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("내용은 20자 이상 1000자 이하입니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 각 큐레이션 카드에는 이미지를 최대 5개까지 등록할 수 있다.")
    @WithMockCurator
    void createCuration9() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder()
            .curationCards(List.of(CurationDomain.createValidCurationCardRequest1().toBuilder()
                .imageIds(List.of(1L, 2L, 3L, 4L, 5L, 6L)).build()))
            .build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("이미지는 최대 5개까지 등록할 수 있습니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 지역에는 빈 값이 들어갈 수 없다.")
    @WithMockCurator
    void createCuration10() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder()
            .area(null).build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("지역은 필수입니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("큐레이션 생성 시, 큐레이션 카드의 개수는 1개 이상 10개 이하이어야 한다.")
    @WithMockCurator
    void createCuration11() throws Exception {
        // given
        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest().toBuilder().
        curationCards(List.of()).build();

        // when
        mockMvc.perform(post("/api/curations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("큐레이션 카드의 개수는 1개 이상 10개 이하이어야 합니다."));

        // then
        then(curationService).shouldHaveNoInteractions();
    }

    @Test
    void deleteCuration() {
    }
}