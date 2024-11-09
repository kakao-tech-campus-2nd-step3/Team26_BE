package org.ktc2.cokaen.wouldyouin.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.ktc2.cokaen.wouldyouin._global.TestData.ImageDomain.createValidMemberImage;
import static org.ktc2.cokaen.wouldyouin._global.TestData.MemberDomain.createValidMember;
import static org.ktc2.cokaen.wouldyouin._global.TestData.MemberDomain.createValidWelcomeMember;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin.Image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._global.TestUtil;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.MemberAdditionalInfoRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit.MemberEditRequest;
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
class MemberServiceUnitTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    MemberImageService memberImageService;

    @Mock
    MemberCreateRequest memberCreateRequest;
    @Mock
    MemberEditRequest memberEditRequest;
    @Mock
    MemberAdditionalInfoRequest memberAdditionalInfoRequest;

    @InjectMocks
    private MemberService memberService;

    private Member validMember;

    @BeforeEach
    void setUp() {
        validMember = createValidMember();
    }

    @Test
    @DisplayName("일반 사용자 생성 테스트")
    void createMember() {
        // given
        given(memberCreateRequest.getProfileImageUrl()).willReturn(validMember.getProfileImageUrl());
        given(memberCreateRequest.toEntity(validMember.getProfileImage())).willReturn(validMember);
        given(memberRepository.save(validMember)).willReturn(validMember);
        given(memberImageService.convert(memberCreateRequest.getProfileImageUrl())).willReturn(validMember.getProfileImage());

        // when
        memberService.createMember(memberCreateRequest);

        // then
        then(memberRepository).should(times(1)).save(validMember);
    }

    @Test
    @DisplayName("사용자 업데이트 테스트")
    void updateMember() {
        // given
        MemberImage validMemberImage = createValidMemberImage(5L);
        Long givenMemberId = validMember.getId();
        given(memberRepository.findById(givenMemberId)).willReturn(Optional.of(validMember));
        given(memberImageService.getById(validMember.getProfileImage().getId())).willReturn(validMemberImage);

        // editRequest의 각 필드는 값을 가지거나 null임
        given(memberEditRequest.getNickname()).willReturn(TestUtil.getOrNull(validMember.getNickname()));
        given(memberEditRequest.getArea()).willReturn(TestUtil.getOrNull(validMember.getArea()));
        given(memberEditRequest.getPhoneNumber()).willReturn(TestUtil.getOrNull(validMember.getPhone()));
        given(memberEditRequest.getProfileImageId()).willReturn(TestUtil.getOrNull(validMember.getId()));

        // when
        memberService.updateMember(givenMemberId, memberEditRequest);

        // then
        then(memberRepository).should(times(1)).findById(validMember.getId());
        var ignore1 = then(memberEditRequest).should(times(1)).getNickname();
        var ignore2 = then(memberEditRequest).should(times(1)).getArea();
        var ignore3 = then(memberEditRequest).should(times(1)).getPhoneNumber();
        var ignore4 = then(memberEditRequest).should(times(1)).getProfileImageId();
    }

    @Test
    @DisplayName("소셜 신규 사용자 추가정보 기입 테스트")
    void updateWelcomeMember() {
        Member validWelcomeMember = createValidWelcomeMember();
        given(memberRepository.findById(validWelcomeMember.getId())).willReturn(Optional.of(validWelcomeMember));
        given(memberAdditionalInfoRequest.getPhone()).willReturn(validWelcomeMember.getPhone());
        given(memberAdditionalInfoRequest.getArea()).willReturn(validWelcomeMember.getArea());
        given(memberAdditionalInfoRequest.getGender()).willReturn(validWelcomeMember.getGender());

        // when
        memberService.updateWelcomeMember(validWelcomeMember.getId(), memberAdditionalInfoRequest);

        // then
        then(memberRepository).should(times(1)).findById(validWelcomeMember.getId());
        var ignore1 = then(memberAdditionalInfoRequest).should(times(1)).getPhone();
        var ignore2 = then(memberAdditionalInfoRequest).should(times(1)).getArea();
        var ignore3 = then(memberAdditionalInfoRequest).should(times(1)).getGender();
    }

    @Test
    @DisplayName("소셜 신규 사용자 추가정보 기입 - welcome 유형 아닌 사용자 사용 테스트")
    void updateWelcomeMember_NormalMemberUsage() {
        given(memberRepository.findById(validMember.getId())).willReturn(Optional.of(validMember));

        // when & then
        // TODO: 커스텀 예외 필요
        assertThrows(RuntimeException.class, () ->
            memberService.updateWelcomeMember(validMember.getId(), memberAdditionalInfoRequest));

        // then
        then(memberRepository).should(times(1)).findById(validMember.getId());
        var ignore1 = then(memberAdditionalInfoRequest).should(never()).getPhone();
        var ignore2 = then(memberAdditionalInfoRequest).should(never()).getArea();
        var ignore3 = then(memberAdditionalInfoRequest).should(never()).getGender();
    }


    @Test
    @DisplayName("사용자 삭제 테스트")
    void deleteById() {
        // given
        Long idToDelete = validMember.getId();
        given(memberRepository.findById(idToDelete)).willReturn(Optional.of(validMember));

        // when
        memberService.deleteById(idToDelete);

        // then
        then(memberRepository).should(times(1)).findById(validMember.getId());
        then(memberRepository).should(times(1)).delete(validMember);
    }

    @Test
    @DisplayName("id로 사용자 응답 생성 테스트")
    void getMemberResponseById() {
        // given
        Long idToGet = validMember.getId();
        given(memberRepository.findById(idToGet)).willReturn(Optional.of(validMember));

        // when
        memberService.getMemberResponseById(idToGet);

        // then
        then(memberRepository).should(times(1)).findById(validMember.getId());
    }

    @Test
    @DisplayName("id로 엔티티 얻어오기 테스트")
    void getByIdOrThrow() {
        // given
        Long idToGet = validMember.getId();
        given(memberRepository.findById(idToGet)).willReturn(Optional.of(validMember));

        // when
        memberService.getByIdOrThrow(idToGet);

        // then
        then(memberRepository).should(times(1)).findById(validMember.getId());
    }

    @Test
    @DisplayName("소셜 id로 엔티티 얻어오기 테스트")
    void getMemberIdentifierBySocialId() {
        // given
        given(memberRepository.findBySocialId(validMember.getSocialId())).willReturn(Optional.of(validMember));

        // when
        memberService.getMemberIdentifierBySocialId(validMember.getSocialId());

        // then
        then(memberRepository).should(times(1)).findBySocialId(validMember.getSocialId());
    }

    @Test
    @DisplayName("대상 사용자 유형 확인테스트")
    void getTargetMemberType() {
        assertThat(memberService.getTargetMemberType()).isEqualTo(MemberType.normal);
    }
}