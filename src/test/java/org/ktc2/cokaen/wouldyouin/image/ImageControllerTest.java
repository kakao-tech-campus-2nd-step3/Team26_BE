package org.ktc2.cokaen.wouldyouin.image;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageController;
import org.ktc2.cokaen.wouldyouin.Image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.Image.application.ImageServiceFactory;
import org.ktc2.cokaen.wouldyouin.Image.application.ImageStorageService;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockMember;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(ImageController.class)
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ImageServiceFactory imageServiceFactory;

    @MockBean
    private CurationImageService curationImageService;

    @MockBean
    private ImageStorageService imageStorageService;

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
    @DisplayName("이미지 경로를 통해 이미지를 조회한다.")
    @WithMockMember
    void getImage() throws Exception {
        // given
        String directory = "member";
        String file = UUID.randomUUID().toString() + ".png";

        // when
        mockMvc.perform(get("/api/images/" + directory + "/" + file))
            .andDo(print())
            .andExpect(status().isOk());

//         then
        then(imageStorageService).should(times(1)).readFromDirectory(eq(Paths.get(directory, file)));
    }

//    @PostMapping
//    public ResponseEntity<ApiResponseBody<List<ImageResponse>>> uploadImages(
//        @RequestParam List<MultipartFile> images,
//        @RequestParam(value = "type") ImageDomain imageDomain) {
//        return ApiResponse.ok(imageServiceFactory.getImageService(imageDomain).saveImages(images));
//    }

    @Test
    @WithMockMember
    void uploadImages() throws Exception {
//        // given
//        MockMultipartFile image1 = ImageData.createValidMultipartFile1();
//        MockMultipartFile image2 = ImageData.createValidMultipartFile2();
//        given((CurationImageService) imageServiceFactory.getImageService(ImageDomain.CURATION)).willReturn(curationImageService);
//        given(curationImageService.saveImages(List.of(image1, image2)))
//            .willReturn(List.of(ImageData.createValidImageResponse1(), ImageData.createValidImageResponse2()));
//
////        // when
//        mockMvc.perform(multipart("/api/images")
//                .file(ImageData.createValidMultipartFile1())
//                .file(ImageData.createValidMultipartFile2())
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .param("type", ImageDomain.CURATION.name())
//                .with(csrf()))
//            .andDo(print())
//            .andExpect(status().isOk());
//            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // 응답 콘텐츠 타입 확인
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].imageName").value("image1.png")) // 첫 번째 이미지 이름 확인
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].imageName").value("image2.jpg")); // 두 번째 이미지 이름 확인
//        ArgumentCaptor<CurationCreateRequest> captor = ArgumentCaptor.forClass(CurationCreateRequest.class);
//        CurationCreateRequest request = CurationDomain.createValidCurationCreateRequest();
//
//        // when
//        mockMvc.perform(post("/api/curations")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andDo(print())
//            .andExpect(status().isCreated());
//
//        // then
//        then(curationService).should(times(1)).create(eq(MemberDomain.validCuratorId), captor.capture());
//        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    void deleteImage() {
    }
}