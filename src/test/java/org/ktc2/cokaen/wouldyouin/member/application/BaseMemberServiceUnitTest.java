package org.ktc2.cokaen.wouldyouin.member.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.host1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.normal1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.welcome1;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.exception.EmailAlreadyExistsException;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BaseMemberServiceUnitTest {

    @Mock
    private BaseMemberRepository baseMemberRepository;
    @Mock
    private DerivedMemberServiceFactory derivedMemberServiceFactory;

    @Mock
    private MemberServiceCommonBehavior mockMemberService;
    @Mock
    private MemberServiceCommonBehavior mockHostService;
    @Mock
    private MemberServiceCommonBehavior mockCuratorService;

    @Mock
    private MemberResponse memberResponse;

    @InjectMocks
    private BaseMemberService baseMemberService;

    private static Map<Long, BaseMember> members;
    private static final Member validMember = MemberData.normal1.entity.get();
    private static final Member validWelcomeMember = MemberData.welcome1.entity.get();
    private static final Host validHost = MemberData.host1.entity.get();
    private static final Curator validCurator = MemberData.curator1.entity.get();

    @BeforeAll
    static void beforeAll() {
        members = Map.of(
            validMember.getId(), validMember,
            validHost.getId(), validHost,
            validCurator.getId(), validCurator,
            validWelcomeMember.getId(), validWelcomeMember);
    }

    @BeforeEach
    void setUp() {
        given(baseMemberRepository.findById(validMember.getId())).willReturn(Optional.of(validMember));
        given(baseMemberRepository.findById(validWelcomeMember.getId())).willReturn(Optional.of(validWelcomeMember));
        given(baseMemberRepository.findById(validHost.getId())).willReturn(Optional.of(validHost));
        given(baseMemberRepository.findById(validCurator.getId())).willReturn(Optional.of(validCurator));
    }

    // welcome 멤버는 normal 멤버로 취급한다. (normalMember 리포지토리에서 탐색하므로)
    MemberType welcomeTypeMapping(MemberType memberType) {
        if (memberType == MemberType.welcome) {
            return MemberType.normal;
        }
        return memberType;
    }

    @ParameterizedTest
    @ValueSource(longs = {normal1.id, curator1.id, host1.id, welcome1.id})
    @DisplayName("임의의 사용자를 찾는 메서드 테스트")
    void getByIdOrThrow(long id) {
        // when
        var expected = members.get(id);
        var actual = baseMemberService.getByIdOrThrow(id);

        // then
        assertEquals(expected, actual);
        then(baseMemberRepository).should(times(1)).findById(id);
    }

    @ParameterizedTest
    @ValueSource(longs = {normal1.id, curator1.id, host1.id, welcome1.id})
    @DisplayName("임의의 사용자의 타입을 반환하는 메서드 테스트")
    void getMemberType(long id) {
        // when
        var expected = members.get(id).getMemberType();
        var actual = baseMemberService.getMemberType(id);

        // then
        assertEquals(expected, actual);
        then(baseMemberRepository).should(times(1)).findById(id);
    }

    @ParameterizedTest
    @ValueSource(longs = {normal1.id, curator1.id, host1.id, welcome1.id})
    @DisplayName("각 유형의 사용자를 찾고 응답 반환하는 메서드 테스트")
    void findById(long id) {
        // given
        given(derivedMemberServiceFactory.get(MemberType.normal)).willReturn(mockMemberService);
        given(derivedMemberServiceFactory.get(MemberType.host)).willReturn(mockHostService);
        given(derivedMemberServiceFactory.get(MemberType.curator)).willReturn(mockCuratorService);

        given(mockMemberService.getMemberResponseById(any())).willReturn(memberResponse);
        given(mockHostService.getMemberResponseById(any())).willReturn(memberResponse);
        given(mockCuratorService.getMemberResponseById(any())).willReturn(memberResponse);

        // when
        baseMemberService.findById(id);

        // then
        MemberType mappedType = welcomeTypeMapping(members.get(id).getMemberType());
        then(derivedMemberServiceFactory).should(times(1)).get(mappedType);
        then(baseMemberRepository).should(times(1)).findById(id);
    }

    @ParameterizedTest
    @ValueSource(longs = {normal1.id, curator1.id, host1.id, welcome1.id})
    @DisplayName("중복 있는 이메일 있는 경우 테스트")
    void checkUniqueEmailOrThrow(long id) {
        // given
        String email = members.get(id).getEmail();
        Optional<BaseMember> optionalMember = Optional.of(members.get(id));
        given(baseMemberRepository.findByEmail(email)).willReturn(optionalMember);

        // when & then
        assertThrows(EmailAlreadyExistsException.class, () -> baseMemberService.checkUniqueEmailOrThrow(email));
        then(baseMemberRepository).should(times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("고유한 이메일을 찾은 경우 테스트")
    void checkUniqueEmailOrThrow_Unique() {
        // given
        String uniqueEmail = "unique1@example.com";
        given(baseMemberRepository.findByEmail(uniqueEmail)).willReturn(Optional.empty());

        // when
        baseMemberService.checkUniqueEmailOrThrow(uniqueEmail);

        // then
        then(baseMemberRepository).should(times(1)).findByEmail(uniqueEmail);
    }

    @ParameterizedTest
    @ValueSource(longs = {normal1.id, curator1.id, host1.id, welcome1.id})
    @DisplayName("각 유형의 사용자를 제거하는 메서드 테스트")
    void deleteById(long id) {
        // given
        given(derivedMemberServiceFactory.get(MemberType.normal)).willReturn(mockMemberService);
        given(derivedMemberServiceFactory.get(MemberType.host)).willReturn(mockHostService);
        given(derivedMemberServiceFactory.get(MemberType.curator)).willReturn(mockCuratorService);
        willDoNothing().given(mockMemberService).deleteById(any());
        willDoNothing().given(mockHostService).deleteById(any());
        willDoNothing().given(mockCuratorService).deleteById(any());

        // when
        baseMemberService.deleteById(id);

        // then
        MemberType mappedType = welcomeTypeMapping(members.get(id).getMemberType());
        then(derivedMemberServiceFactory).should(times(1)).get(mappedType);
        then(baseMemberRepository).should(times(1)).deleteById(id);
    }
}