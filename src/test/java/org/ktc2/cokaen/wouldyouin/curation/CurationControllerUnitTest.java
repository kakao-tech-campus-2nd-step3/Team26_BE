package org.ktc2.cokaen.wouldyouin.curation;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin._common.config.ParamDefaults;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.ktc2.cokaen.wouldyouin.curation.api.CurationController;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.application.CurationService;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(CurationController.class)
class CurationControllerUnitTest {

    private static Long id;

    private static Host validHost;
    private static ObjectMapper objectMapper;
    @MockBean
    private CurationService curationService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @MockBean
    private EventSliceResponse eventSliceResponse;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    public static void init() {
        id = 3L;
//        validHost = createValidHost();
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

    CurationSliceResponse curationSliceResponse = CurationSliceResponse.builder()
        .curations(List.of())
        .slice(SliceInfo.builder()
            .sliceSize(10)
            .lastId(Long.MAX_VALUE)
            .build()
        )
        .build();

    CurationResponse curationResponse = CurationResponse.builder()
        .build();

    private String title;
    private String content;
    @Valid
    private List<CurationCardRequest> curationCards;
    @NotNull(message = "지역은 필수입니다.")
    private Area area;
    private List<String> hashTag;
    private List<Long> eventIds;

    @NotEmpty(message = "부제목은 필수입니다.")
    private String subtitle;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 20, max = 1000, message = "내용은 20자 이상 1000자 이하입니다.")
    private String content;

    CurationCreateRequest curationCreateRequest = CurationCreateRequest.builder()
        .title("title")
        .content("content")
        .area(Area.전체)
        .build();

    @Test
    @DisplayName("RequestParam을 통해 전달받은 지역의 큐레이션 목록을 조회한다.")
    void getCurationsByAreaOrderByCreatedDateDesc0() throws Exception {
        // given
        given(curationService.getAllByAreaOrderByCreatedDateDesc(
            Area.광주, PageRequest.of(5, 20), 100L))
            .willReturn(curationSliceResponse);

        // when
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
    void getCurationsByAreaOrderByCreatedDateDesc1() throws Exception {
        // given
        given(curationService.getAllByAreaOrderByCreatedDateDesc(
            Area.valueOf(ParamDefaults.AREA),
            PageRequest.of(Integer.decode(ParamDefaults.PAGE), Integer.decode(ParamDefaults.PAGE_SIZE)),
            Long.decode(ParamDefaults.LAST_ID))
        )
            .willReturn(curationSliceResponse);

        // when
        mockMvc.perform(get("/api/curations")).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getAllByAreaOrderByCreatedDateDesc(
            eq(Area.전체), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("ReqeustParam을 통해 요청할 페이지에 대한 정보를 전달받아, 해당하는 호스트의 큐레이션 목록을 조회한다.")
    void getCurationsByCuratorIdOrderByCreatedDateDesc0() throws Exception {
        // given
        given(curationService.getAllByCuratorIdOrderByCreatedDateDesc(
            id, PageRequest.of(5, 20), 100L))
            .willReturn(curationSliceResponse);

        // when
        mockMvc.perform(get("/api/curations/curators/" + id)).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getAllByCuratorIdOrderByCreatedDateDesc(
            eq(id), eq(PageRequest.of(5, 20)), eq(100L));
    }

    @Test
    @DisplayName("RequestParam을 통해 페이지 정보를 지정하지 않은 경우, 디폴트 값으로 해당하는 호스트의 큐레이션 목록을 조회한다.")
    void getCurationsByCuratorIdOrderByCreatedDateDesc1() throws Exception {
        // given
        given(curationService.getAllByCuratorIdOrderByCreatedDateDesc(
            id,
            PageRequest.of(Integer.decode(ParamDefaults.PAGE), Integer.decode(ParamDefaults.PAGE_SIZE)),
            Long.decode(ParamDefaults.LAST_ID)))
            .willReturn(curationSliceResponse);

        // when
        mockMvc.perform(get("/api/curations/curators/" + id)).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getAllByCuratorIdOrderByCreatedDateDesc(
            eq(id), eq(PageRequest.of(0, 10)), eq(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("큐레이션 ID를 통해 해당하는 큐레이션을 조회한다.")
    void getCurationByCurationId() throws Exception {
        // given
        given(curationService.getById(id)).willReturn(curationResponse);

        // when
        mockMvc.perform(get("/api/curations/" + id)).andDo(print())
            .andExpect(status().isOk());

        // then
        then(curationService).should(times(1)).getById(eq(id));
    }

//    @PostMapping
//    public ResponseEntity<ApiResponseBody<CurationResponse>> createCuration(
//        @Valid @RequestBody CurationCreateRequest curationCreateRequest,
//        @Authorize(MemberType.curator) MemberIdentifier curator) {
//        return ApiResponse.created(curationService.create(curator.id(), curationCreateRequest));
//    }

    @Test
    @DisplayName("리퀘스트 바디로 전달받은 정보를 통해 큐레이션을 생성한다.")
    void createCuration() {
    }

    @Test
    void updateCuration() {
    }

    @Test
    void deleteCuration() {
    }
}