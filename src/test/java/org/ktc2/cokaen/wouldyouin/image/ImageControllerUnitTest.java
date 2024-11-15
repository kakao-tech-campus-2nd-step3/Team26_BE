package org.ktc2.cokaen.wouldyouin.image;

import static java.lang.Math.abs;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin._global.mockmember.WithMockCurator1;
import org.ktc2.cokaen.wouldyouin._global.mockmember.WithMockMember1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.mockMultipartFile1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.mockMultipartFile2;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.image.api.ImageController;
import org.ktc2.cokaen.wouldyouin.image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.image.application.CurationImageService;
import org.ktc2.cokaen.wouldyouin.image.application.ImageServiceFactory;
import org.ktc2.cokaen.wouldyouin.image.application.ImageStorageService;
import org.ktc2.cokaen.wouldyouin.payment.application.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(ImageController.class)
class ImageControllerUnitTest {

    private final long randomId = abs(new Random().nextLong());
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
    private PaymentService paymentService;
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
    @WithMockMember1
    void getImage() throws Exception {
        // given
        String directory = "member";
        String file = "image.jpg";

        // when
        mockMvc.perform(get("/api/images/{directory}/{file}", directory, file))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(imageStorageService).should(times(1))
            .readFromDirectory(eq(Paths.get(directory, file)));
    }

    @Test
    @DisplayName("이미지 경로를 통해 썸네일 이미지를 조회한다.")
    @WithMockMember1
    void getThumnailImage() throws Exception {
        // given
        String directory = "member";
        String thumbnail = "thumbnail";
        String file = "image.jpg";

        // when
        mockMvc.perform(get("/api/images/{directory}/{thumbnail}/{file}", directory, thumbnail, file))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(imageStorageService).should(times(1)).readFromDirectory(eq(Paths.get(directory, thumbnail, file)));
    }

    @Test
    @DisplayName("RequestParam으로 이미지 도메인을 받아 첨부된 이미지를 업로드한다.")
    @WithMockCurator1
    void uploadImages1() throws Exception {
        // given
        MockMultipartFile image1 = mockMultipartFile1.get();
        MockMultipartFile image2 = mockMultipartFile2.get();
        given((CurationImageService) imageServiceFactory.getImageService(
            ImageDomain.CURATION)).willReturn(curationImageService);
        given(curationImageService.saveImages(List.of(image1, image2)))
            .willReturn(
                List.of(ImageData.curation1.response.get(), ImageData.curation2.response.get()));

        // when
        mockMvc.perform(multipart("/api/images")
                .file(image1)
                .file(image2)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("type", ImageDomain.CURATION.name())
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(imageServiceFactory).should(times(1)).getImageService(eq(ImageDomain.CURATION));
        then(curationImageService).should(times(1)).saveImages(List.of(image1, image2));
    }

    @Test
    @DisplayName("RequestParam의 이미지 도메인의 값으로는 MEMBER, CURATION, ADVERTISEMENT, EVENT만 사용할 수 있다.")
    @WithMockCurator1
    void uploadImages2() throws Exception {
        // given
        MockMultipartFile image1 = mockMultipartFile1.get();
        MockMultipartFile image2 = mockMultipartFile2.get();

        // when
        mockMvc.perform(multipart("/api/images")
                .file(image1)
                .file(image2)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("type", "INVALID")
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("해당 이미지 도메인에 대한 서비스가 존재하지 않습니다."));

        // then
        then(imageServiceFactory).shouldHaveNoInteractions();
        then(curationImageService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("PathVariable로 이미지의 ID를 받아 이미지를 삭제한다.")
    @WithMockCurator1
    void deleteImage1() throws Exception {
        // given
        given((CurationImageService) imageServiceFactory.getImageService(
            ImageDomain.CURATION)).willReturn(curationImageService);

        // when
        mockMvc.perform(delete("/api/images/" + randomId)
                .param("type", ImageDomain.CURATION.name())
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isNoContent());

        // then
        then(imageServiceFactory).should(times(1)).getImageService(eq(ImageDomain.CURATION));
        then(curationImageService).should(times(1))
            .deleteImage(curator1.memberIdentifier, randomId);
    }

    @Test
    @DisplayName("RequestParam의 이미지 도메인의 값으로는 MEMBER, CURATION, ADVERTISEMENT, EVENT만 사용할 수 있다.")
    @WithMockCurator1
    void deleteImage2() throws Exception {
        // given
        given((CurationImageService) imageServiceFactory.getImageService(
            ImageDomain.CURATION)).willReturn(curationImageService);

        // when
        mockMvc.perform(delete("/api/images/" + randomId)
                .param("type", "INVALID")
                .with(csrf()))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("해당 이미지 도메인에 대한 서비스가 존재하지 않습니다."));

        // then
        then(imageServiceFactory).shouldHaveNoInteractions();
        then(curationImageService).shouldHaveNoInteractions();
    }
}