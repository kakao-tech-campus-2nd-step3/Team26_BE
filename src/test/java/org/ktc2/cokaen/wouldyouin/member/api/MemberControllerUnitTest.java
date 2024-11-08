package org.ktc2.cokaen.wouldyouin.member.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ktc2.cokaen.wouldyouin.auth.application.CustomUserDetailsService;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(MemberController.class)
class MemberControllerUnitTest {

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private BaseMemberService baseMemberService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private HostService hostService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void updateMember() {
    }

    @Test
    void findMember() {
    }

    @Test
    void deleteMember() {
    }
}