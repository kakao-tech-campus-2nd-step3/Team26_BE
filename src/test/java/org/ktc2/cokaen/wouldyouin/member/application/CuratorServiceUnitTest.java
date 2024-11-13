package org.ktc2.cokaen.wouldyouin.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin.Image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.CuratorRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CuratorServiceUnitTest {

    @Mock
    private CuratorRepository curatorRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BaseMemberRepository baseMemberRepository;
    @Mock
    private MemberImageService memberImageService;

    @InjectMocks
    private CuratorService curatorService;

    private Curator validCurator;

    @BeforeEach
    void setUp() {
        validCurator = MemberData.curator1.entity.get();
    }

    @Test
    @DisplayName("큐레이터 사용자 생성 테스트")
    void createCurator() {
        // given
        Member validMember = MemberData.normal1.entity.get();
        given(memberRepository.findById(validMember.getId())).willReturn(Optional.of(validMember));

        // when
        curatorService.createCurator(validMember.getId());

        // then
        then(memberRepository).should(times(1)).deleteById(validMember.getId());
        then(baseMemberRepository).should(times(1)).deleteById(validMember.getId());
        then(memberRepository).should(times(1)).flush();
        then(baseMemberRepository).should(times(1)).flush();
        then(curatorRepository).should(times(1)).save(any(Curator.class));
    }

//    @Test
//    @DisplayName("")
//    void updateCurator() {
//        // given
//        Long newProfileImageId = 5L;
//        MemberImage newProfileImage = createValidMemberImage(newProfileImageId);
//        CuratorEditRequest editRequest = CuratorEditRequest.curatorEditRequestBuilder()
//            .nickname(TestUtil.getOrNull("newNickname"))
//            .phoneNumber(TestUtil.getOrNull("010-1010-8888"))
//            .profileImageId(TestUtil.getOrNull(newProfileImageId))
//            .area(Area.광주)
//            .intro(TestUtil.getOrNull("new intro"))
//            .build();
//
//        given(curatorRepository.findById(validCurator.getId())).willReturn(Optional.of(validCurator));
//        given(memberImageService.getById(newProfileImageId)).willReturn(newProfileImage);
//
//        // when
//        curatorService.updateCurator(validCurator.getId(), editRequest);
//
//        // then
//        then(curatorRepository).should(times(1)).findById(validCurator.getId());
//        int times = 1;
//        if (editRequest.getProfileImageId() == null) {
//            times = 0;
//        }
//        then(memberImageService).should(times(times)).getById(newProfileImageId);
//    }

    @Test
    @DisplayName("큐레이터 삭제 테스트")
    void deleteById() {
        // given
        given(curatorRepository.findById(validCurator.getId())).willReturn(Optional.of(validCurator));

        // when
        curatorService.deleteById(validCurator.getId());

        // then
        then(curatorRepository).should(times(1)).delete(validCurator);
    }

    @Test
    @DisplayName("id로 사용자 응답 생성 테스트")
    void getMemberResponseById() {
        // given
        given(curatorRepository.findById(validCurator.getId())).willReturn(Optional.of(validCurator));

        // when
        curatorService.getMemberResponseById(validCurator.getId());

        // then
        then(curatorRepository).should(times(1)).findById(validCurator.getId());
    }

    @Test
    @DisplayName("id로 큐레이터 얻어오기 테스트")
    void getByIdOrThrow() {
        // given
        given(curatorRepository.findById(validCurator.getId())).willReturn(Optional.of(validCurator));

        // when
        curatorService.getByIdOrThrow(validCurator.getId());

        // then
        then(curatorRepository).should(times(1)).findById(validCurator.getId());
    }

    @Test
    @DisplayName("대상 사용자 유형 확인 테스트")
    void getTargetMemberType() {
        assertThat(curatorService.getTargetMemberType()).isEqualTo(MemberType.curator);
    }

    @Test
    @DisplayName("LikeableMemberService 얻어오기 테스트")
    void getLikeableMemberService() {
        assertThat(curatorService.getLikeableMemberService()).isEqualTo(curatorService);
    }
}