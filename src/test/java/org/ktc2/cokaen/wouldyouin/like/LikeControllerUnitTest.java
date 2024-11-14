package org.ktc2.cokaen.wouldyouin.like;

import static java.lang.Math.abs;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockCurator1;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockHost1;
import org.ktc2.cokaen.wouldyouin._global.mockMember.WithMockMember1;
import org.ktc2.cokaen.wouldyouin._global.testdata.LikeData;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.normal1;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.like.api.LikeController;
import org.ktc2.cokaen.wouldyouin.like.api.dto.LikeSliceResponse;
import org.ktc2.cokaen.wouldyouin.like.application.CuratorLikeService;
import org.ktc2.cokaen.wouldyouin.like.application.HostLikeService;
import org.ktc2.cokaen.wouldyouin.like.application.LikeServiceFactory;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(LikeController.class)
public class LikeControllerUnitTest {

    private final long randomId = abs(new Random().nextLong());
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private LikeServiceFactory likeServiceFactory;
    @MockBean
    private CuratorLikeService curatorLikeService;
    @MockBean
    private HostLikeService hostLikeService;
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
    @DisplayName("jwt 토큰 정보에 해당하는 member의 Curator 좋아요 목록을 조회한다.")
    @WithMockMember1
    void getLikes1() throws Exception {
        // given
        MemberIdentifier identifier = normal1.memberIdentifier;
        MemberType targetMemberType = MemberType.curator;
        LikeSliceResponse sliceResponse = LikeData.sliceResponse.normal1.curatorLikes.get();
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long beforeLastId = sliceResponse.getLikes().getLast().getMemberId();

        given((CuratorLikeService) likeServiceFactory.getLikeServiceFrom(targetMemberType))
            .willReturn(curatorLikeService);
        given(curatorLikeService.getLikes(eq(identifier), eq(pageable), eq(beforeLastId)))
            .willReturn(sliceResponse);

        // when
        mockMvc.perform(get("/api/likes")
                .param("type", targetMemberType.name())
                .param("page", Integer.toString(pageNumber))
                .param("size", Integer.toString(pageSize))
                .param("lastId", beforeLastId.toString()))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(likeServiceFactory).should(times(1)).getLikeServiceFrom(eq(targetMemberType));
        then(curatorLikeService).should(times(1))
            .getLikes(eq(identifier), eq(pageable), eq(beforeLastId));
    }

    @Test
    @DisplayName("jwt 토큰 정보에 해당하는 member의 Host 좋아요 목록을 조회한다.")
    @WithMockMember1
    void getLikes2() throws Exception {
        // given
        MemberIdentifier identifier = normal1.memberIdentifier;
        MemberType targetMemberType = MemberType.host;
        LikeSliceResponse sliceResponse = LikeData.sliceResponse.normal1.hostLikes.get();
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long beforeLastId = sliceResponse.getLikes().getLast().getMemberId();

        given((HostLikeService) likeServiceFactory.getLikeServiceFrom(targetMemberType))
            .willReturn(hostLikeService);
        given(hostLikeService.getLikes(eq(identifier), eq(pageable), eq(beforeLastId)))
            .willReturn(sliceResponse);

        // when
        mockMvc.perform(get("/api/likes")
                .param("type", targetMemberType.name())
                .param("page", Integer.toString(pageNumber))
                .param("size", Integer.toString(pageSize))
                .param("lastId", beforeLastId.toString()))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(likeServiceFactory).should(times(1)).getLikeServiceFrom(eq(targetMemberType));
        then(hostLikeService).should(times(1))
            .getLikes(eq(identifier), eq(pageable), eq(beforeLastId));
    }

    @Test
    @DisplayName("jwt 토큰 정보에 해당하는 curator의 Host 좋아요 목록을 조회한다.")
    @WithMockCurator1
    void getLikes3() throws Exception {
        // given
        MemberIdentifier identifier = curator1.memberIdentifier;
        MemberType targetMemberType = MemberType.host;
        LikeSliceResponse sliceResponse = LikeData.sliceResponse.curator1.hostLikes.get();
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long beforeLastId = sliceResponse.getLikes().getLast().getMemberId();

        given((HostLikeService) likeServiceFactory.getLikeServiceFrom(targetMemberType))
            .willReturn(hostLikeService);
        given(hostLikeService.getLikes(eq(identifier), eq(pageable), eq(beforeLastId)))
            .willReturn(sliceResponse);

        // when
        mockMvc.perform(get("/api/likes")
                .param("type", targetMemberType.name())
                .param("page", Integer.toString(pageNumber))
                .param("size", Integer.toString(pageSize))
                .param("lastId", beforeLastId.toString()))
            .andDo(print())
            .andExpect(status().isOk());

        // then
        then(likeServiceFactory).should(times(1)).getLikeServiceFrom(eq(targetMemberType));
        then(hostLikeService).should(times(1))
            .getLikes(eq(identifier), eq(pageable), eq(beforeLastId));
    }

    @Test
    @DisplayName("Member 권한으로 Host ID를 통해 Host 좋아요를 생성한다.")
    @WithMockMember1
    void createOrDeleteLike1() throws Exception {
        // given
        Long beforeLastId = LikeData.sliceResponse.normal1.hostLikes.get().getSliceInfo()
            .getLastId();
        given((HostLikeService) likeServiceFactory.getLikeServiceFrom(MemberType.host))
            .willReturn(hostLikeService);
        given(hostLikeService.toggleLike(eq(normal1.memberIdentifier), eq(beforeLastId)))
            .willReturn(LikeData.toggleResponse.hostLikes.create.get());

        // when
        mockMvc.perform(post("/api/likes/" + randomId)
                .param("type", MemberType.host.name()).with(csrf()))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(likeServiceFactory).should(times(1)).getLikeServiceFrom(eq(MemberType.host));
        then(hostLikeService).should(times(1))
            .toggleLike(eq(normal1.memberIdentifier), eq(randomId));
    }

    @Test
    @DisplayName("Member 권한으로 Host ID를 통해 Host 좋아요를 삭제한다.")
    @WithMockMember1
    void createOrDeleteLike2() throws Exception {
        // given
        Long beforeLastId = LikeData.sliceResponse.normal1.hostLikes.get().getSliceInfo()
            .getLastId();
        given((HostLikeService) likeServiceFactory.getLikeServiceFrom(MemberType.host))
            .willReturn(hostLikeService);
        given(hostLikeService.toggleLike(eq(normal1.memberIdentifier), eq(beforeLastId)))
            .willReturn(LikeData.toggleResponse.hostLikes.delete.get());

        // when
        mockMvc.perform(post("/api/likes/" + randomId)
                .param("type", MemberType.host.name()).with(csrf()))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(likeServiceFactory).should(times(1)).getLikeServiceFrom(eq(MemberType.host));
        then(hostLikeService).should(times(1))
            .toggleLike(eq(normal1.memberIdentifier), eq(randomId));
    }

    @Test
    @DisplayName("Curator 권한으로 Host ID를 통해 Host 좋아요를 생성한다.")
    @WithMockCurator1
    void createOrDeleteLike3() throws Exception {
        // given
        Long beforeLastId = LikeData.sliceResponse.normal1.hostLikes.get().getSliceInfo()
            .getLastId();
        given((HostLikeService) likeServiceFactory.getLikeServiceFrom(MemberType.host))
            .willReturn(hostLikeService);
        given(hostLikeService.toggleLike(eq(normal1.memberIdentifier), eq(beforeLastId)))
            .willReturn(LikeData.toggleResponse.hostLikes.create.get());

        // when
        mockMvc.perform(post("/api/likes/" + randomId)
                .param("type", MemberType.host.name()).with(csrf()))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(likeServiceFactory).should(times(1)).getLikeServiceFrom(eq(MemberType.host));
        then(hostLikeService).should(times(1))
            .toggleLike(eq(curator1.memberIdentifier), eq(randomId));
    }

    @Test
    @DisplayName("Curator 권한으로 Host ID를 통해 Host 좋아요를 삭제한다.")
    @WithMockCurator1
    void createOrDeleteLike4() throws Exception {
        // given
        Long beforeLastId = LikeData.sliceResponse.normal1.hostLikes.get().getSliceInfo()
            .getLastId();
        given((HostLikeService) likeServiceFactory.getLikeServiceFrom(MemberType.host))
            .willReturn(hostLikeService);
        given(hostLikeService.toggleLike(eq(normal1.memberIdentifier), eq(beforeLastId)))
            .willReturn(LikeData.toggleResponse.hostLikes.delete.get());

        // when
        mockMvc.perform(post("/api/likes/" + randomId)
                .param("type", MemberType.host.name()).with(csrf()))
            .andDo(print())
            .andExpect(status().isCreated());

        // then
        then(likeServiceFactory).should(times(1)).getLikeServiceFrom(eq(MemberType.host));
        then(hostLikeService).should(times(1))
            .toggleLike(eq(curator1.memberIdentifier), eq(randomId));
    }

    @Test
    @DisplayName("Host 권한으로 좋아요할 수 없다.")
    @WithMockHost1
    void createOrDeleteLike5() throws Exception {
        // given
        Long beforeLastId = LikeData.sliceResponse.normal1.hostLikes.get().getSliceInfo()
            .getLastId();
        given((HostLikeService) likeServiceFactory.getLikeServiceFrom(MemberType.host))
            .willReturn(hostLikeService);
        given(hostLikeService.toggleLike(eq(normal1.memberIdentifier), eq(beforeLastId)))
            .willReturn(LikeData.toggleResponse.hostLikes.delete.get());

        // when
        mockMvc.perform(post("/api/likes/" + randomId)
                .param("type", MemberType.host.name()).with(csrf()))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("요구된 멤버 형식과 실제 형식이 다릅니다."));

        // then
        then(likeServiceFactory).shouldHaveNoInteractions();
        then(hostLikeService).shouldHaveNoInteractions();
    }
}
