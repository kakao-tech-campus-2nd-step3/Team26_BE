package org.ktc2.cokaen.wouldyouin.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin.Image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin._global.TestUtil;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.create.HostCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit.HostEditRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.HostRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HostServiceUnitTest {

    @Mock
    private HostRepository hostRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MemberImageService memberImageService;

    @Mock
    private HostCreateRequest hostCreateRequest;

    @InjectMocks
    private HostService hostService;

    private Host validHost;

    @BeforeEach
    void setUp() {
        validHost = MemberData.host1.entity.get();
    }

    @Test
    @DisplayName("호스트 사용자 생성 테스트")
    void createHost() {
        // given
        String password = "host0password";
        String hashedPassword = validHost.getHashedPassword();
        MemberImage profileImage = validHost.getProfileImage();
        String thumbnailImageUrl = validHost.getProfileImageThumbnailUrl();
        Long profileImageId = profileImage.getId();

        given(hostCreateRequest.getPassword()).willReturn(password);
        given(hostCreateRequest.getProfileImageId()).willReturn(profileImageId);
        given(passwordEncoder.encode(password)).willReturn(hashedPassword);
        given(memberImageService.getById(profileImageId)).willReturn(profileImage);
        given(memberImageService.createThumbnail(profileImage.getName())).willReturn(thumbnailImageUrl);
        given(hostCreateRequest.toEntity(hashedPassword, profileImage, thumbnailImageUrl)).willReturn(validHost);
        given(hostRepository.save(validHost)).willReturn(validHost);

        // when
        hostService.createHost(hostCreateRequest);

        // then
        var ignore1 = then(hostCreateRequest).should(times(1)).getPassword();
        var ignore2 = then(hostCreateRequest).should(times(1)).getProfileImageId();
        then(passwordEncoder).should(times(1)).encode(password);
        then(memberImageService).should(times(1)).getById(profileImageId);
        then(hostCreateRequest).should(times(1))
            .toEntity(hashedPassword, profileImage, thumbnailImageUrl);
        then(hostRepository).should(times(1)).save(validHost);
        then(memberImageService).should(times(1)).setBaseMember(profileImage, validHost);
    }

    @Test
    @DisplayName("호스트 업데이트 테스트")
    void updateHost() {
        // given
        Long newProfileImageId = 5L;
        MemberImage newProfileImage = ImageData.member.host.entity.get();
        newProfileImage.setBaseMember(validHost);
        HostEditRequest editRequest = HostEditRequest.builder()
            .nickname(TestUtil.getOrNull("newNickname"))
            .phoneNumber(TestUtil.getOrNull("010-1010-8888"))
            .profileImageId(TestUtil.getOrNull(newProfileImageId))
            .intro(TestUtil.getOrNull("new intro"))
            .hashtags(TestUtil.getOrNull(List.of("#new", "#hashtag")))
            .build();

        given(hostRepository.findById(validHost.getId())).willReturn(Optional.of(validHost));
        given(memberImageService.getById(newProfileImageId)).willReturn(newProfileImage);

        // when
        hostService.updateHost(validHost.getId(), editRequest);

        // then
        then(hostRepository).should(times(1)).findById(validHost.getId());
        int times = 1;
        if (editRequest.getProfileImageId() == null) {
            times = 0;
        }
        then(memberImageService).should(times(times)).getById(newProfileImageId);
    }

    @Test
    @DisplayName("호스트 삭제 테스트")
    void deleteById() {
        // given
        given(hostRepository.findById(validHost.getId())).willReturn(Optional.of(validHost));

        // when
        hostService.deleteById(validHost.getId());

        // then
        then(hostRepository).should(times(1)).delete(validHost);
    }

    @Test
    @DisplayName("id로 사용자 응답 생성 테스트")
    void getMemberResponseById() {
        // given
        given(hostRepository.findById(validHost.getId())).willReturn(Optional.of(validHost));

        // when
        hostService.getMemberResponseById(validHost.getId());

        // then
        then(hostRepository).should(times(1)).findById(validHost.getId());
    }

    @Test
    @DisplayName("로그인 요청으로 사용자 응답 테스트")
    void getMemberResponseBy() {
        // given
        String password = "host0password";
        LocalLoginRequest loginRequest = new LocalLoginRequest(validHost.getEmail(), password);
        given(passwordEncoder.encode(password)).willReturn(validHost.getHashedPassword());
        given(hostRepository.findByEmailAndHashedPassword(
            validHost.getEmail(), validHost.getHashedPassword()))
            .willReturn(Optional.of(validHost));

        // when
        hostService.getMemberResponseBy(loginRequest);

        // then
        then(passwordEncoder).should(times(1)).encode(password);
        then(hostRepository).should(times(1)).findByEmailAndHashedPassword(
            validHost.getEmail(), validHost.getHashedPassword());
    }

    @Test
    @DisplayName("id로 호스트 얻어오기 테스트")
    void getByIdOrThrow() {
        // given
        given(hostRepository.findById(validHost.getId())).willReturn(Optional.of(validHost));

        // when
        hostService.getByIdOrThrow(validHost.getId());

        // then
        then(hostRepository).should(times(1)).findById(validHost.getId());
    }

    @Test
    @DisplayName("대상 사용자 유형 확인 테스트")
    void getTargetMemberType() {
        assertThat(hostService.getTargetMemberType()).isEqualTo(MemberType.host);
    }

    @Test
    @DisplayName("LikeableMemberService 얻어오기 테스트")
    void getLikeableMemberService() {
        assertThat(hostService.getLikeableMemberService()).isEqualTo(hostService);
    }
}