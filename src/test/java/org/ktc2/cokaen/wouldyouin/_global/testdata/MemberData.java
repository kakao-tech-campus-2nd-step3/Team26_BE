package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.ReservationMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.CurationCuratorResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberData {

    public static class R {
        public static class normal {
            public static final long id = 101L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.normal);
            public static final AccountType accountType = AccountType.kakao;
            public static final String email = "member1@example.com";
            public static final String nickname = "nick_normal_123";
            public static final String phone = "010-1112-2233";
            public static final MemberImage profileImage = ImageData.member.normal.entity.get();
            public static final Area area = Area.광주;
            public static final String gender = "Men";
            public static final String socialId = "100100100100100";
            public static final MemberType memberType = MemberType.normal;
        }
        public static class curator {
            public static final long id = 102L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.curator);
            public static final AccountType accountType = AccountType.google;
            public static final String email = "curator1@example.com";
            public static final String nickname = "nick_curator_12";
            public static final String phone = "010-4545-6767";
            public static final MemberImage profileImage = ImageData.member.curator.entity.get();
            public static final String profileImageUrl = "https://wouldyouin.store/api/images/member/" + profileImage.getName();
            public static final Area area = Area.광주;
            public static final String gender = "Women";
            public static final String socialId = "200200200200200";
            public static final String intro = "큐레이터 자기소개입니다.";
            public static final List<String> hashtags = List.of("#큐레이터", "#해시태그", "#입니다");
        }
        public static class host {
            public static final long id = 103L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.host);
            public static final String email = "host1@example.com";
            public static final String nickname = "nick_host_12";
            public static final String phone = "010-4545-6767";
            public static final String hashedPassword = "hashed_password";
            public static final MemberImage profileImage = ImageData.member.host.entity.get();
            public static final String intro = "주최자 자기소개입니다.";
            public static final List<String> hashtags = List.of("#주최자", "#해시태그", "#입니다");

        }
        public static class welcome {
            public static final long id = 104L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.welcome);
            public static final AccountType accountType = AccountType.kakao;
            public static final String email = "welcome2@example.com";
            public static final String nickname = "nick_normal_333";
            public static final String phone = "010-4414-1144";
            public static final MemberImage profileImage = ImageData.member.welcome.entity.get();
            public static final Area area = Area.서울;
            public static final String gender = "Men";
            public static final String socialId = "456456456456";

        }
        public static class admin {
            public static final long id = 105L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.admin);
        }
    }

    public static class normal {
        public static class entity {
            public static Member get() {
                Member ret = Member.builder()
                    .accountType(R.normal.accountType)
                    .email(R.normal.email)
                    .nickname(R.normal.nickname)
                    .phone(R.normal.phone)
                    .profileImage(R.normal.profileImage)
                    .area(R.normal.area)
                    .gender(R.normal.gender)
                    .socialId(R.normal.socialId)
                    .build();
                ReflectionTestUtils.setField(ret, "Id", R.normal.id);
                ReflectionTestUtils.setField(ret, "memberType", R.normal.memberType);
//                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }

    public static class curator {
        public static class entity {
            public static Curator get() {
                Curator ret = Curator.curatorBuilder()
                    .accountType(R.curator.accountType)
                    .email(R.curator.email)
                    .nickname(R.curator.nickname)
                    .phone(R.curator.phone)
                    .profileImage(R.curator.profileImage)
                    .area(R.curator.area)
                    .gender(R.curator.gender)
                    .socialId(R.curator.socialId)
                    .build();
                ReflectionTestUtils.setField(ret, "Id", R.curator.id);
                ReflectionTestUtils.setField(ret, "intro", R.curator.intro);
                ReflectionTestUtils.setField(ret, "hashtags", R.curator.hashtags);
//                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }

    public static class host {
        public static class entity {
            public static Host get() {
                Host ret = Host.builder()
                    .email(R.host.email)
                    .nickname(R.host.nickname)
                    .phone(R.host.phone)
                    .hashedPassword(R.host.hashedPassword)
                    .profileImage(R.host.profileImage)
                    .build();
                ReflectionTestUtils.setField(ret, "Id", R.host.id);
                ReflectionTestUtils.setField(ret, "intro", R.host.intro);
                ReflectionTestUtils.setField(ret, "hashtags", R.host.hashtags);
//                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }

    public static class welcome {
        public static class entity {
            public static Member get() {
                Member ret = Member.builder()
                    .accountType(R.welcome.accountType)
                    .email(R.welcome.email)
                    .nickname(R.welcome.nickname)
                    .phone(R.welcome.phone)
                    .profileImage(R.welcome.profileImage)
                    .area(R.welcome.area)
                    .gender(R.welcome.gender)
                    .socialId(R.welcome.socialId)
                    .build();
                ReflectionTestUtils.setField(ret, "Id", R.welcome.id);
//                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }

    public static class admin {
        public static class entity {

            public static final long validAdminId = 105L;
        }
        public static class request {

        }
        public static class response {

        }
    }

    public static class response {
        public static class reservationMember {
            public static ReservationMemberResponse get() {
                return ReservationMemberResponse.builder()
                    .memberId(R.normal.id)
                    .email(R.normal.email)
                    .nickname(R.normal.nickname)
                    .phone(R.normal.phone)
                    .gender(R.normal.gender)
                    .build();
            }
        }
        public static class curationCurator {
            public static CurationCuratorResponse get() {
                return CurationCuratorResponse.builder()
                    .nickname(R.curator.nickname)
                    .email(R.curator.email)
                    .phone(R.curator.phone)
                    .profileImageUrl(R.curator.profileImageUrl)
                    .intro(R.curator.intro)
                    .likes(0)
                    .hashtags(R.curator.hashtags)
                    .build();
            }
        }
    }
}