package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLike;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLike;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.CurationCuratorResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.ReviewMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.ReservationMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Gender;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberData {

    public static class R {

        public static class normal1 {

            public static class _Relation {

                public static List<CuratorLike> curatorLikes() {
                    return null; // TODO: Implement
                }

                public static List<HostLike> hostLikes() {
                    return null; // TODO: Implement
                }

                public static List<Reservation> reservations() {
                    return null; // TODO: Implement
                }

                public static List<Review> reviews() {
                    return null; // TODO: Implement
                }
            }

            public static final long id = 101L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.normal);
            public static final AccountType accountType = AccountType.kakao;
            public static final String email = "member1@example.com";
            public static final String nickname = "nick_normal_123";
            public static final String phone = "010-1112-2233";
            public static final MemberImage profileImage = ImageData.member.normal.entity.get();
            public static final String profileImageUrl = ImageData.R.member.normal.url;
            public static final String profileImageThumbnailUrl = ImageData.getThumbnailUrl(profileImage);
            public static final Area area = Area.광주;
            public static final Gender gender = Gender.MAN;
            public static final String socialId = "100100100100100";
            public static final MemberType memberType = MemberType.normal;
        }

        public static class curator1 {

            public static class _Relation {

                public static List<Curation> curations() {
                    return null; // TODO: Implement
                }
            }

            public static final long id = 201L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.curator);
            public static final AccountType accountType = AccountType.google;
            public static final String email = "curator1@example.com";
            public static final String nickname = "nick_curator_12";
            public static final String phone = "010-4545-6767";
            public static final Integer likes = 10;
            public static final MemberImage profileImage = ImageData.member.curator.entity.get();
            public static final String profileImageUrl = ImageData.R.member.curator.url;
            public static final String profileImageThumbnailUrl = ImageData.getThumbnailUrl(profileImage);
            public static final Area area = Area.광주;
            public static final Gender gender = Gender.WOMAN;
            public static final String socialId = "200200200200200";
            public static final String intro = "큐레이터 자기소개입니다.";
            public static final List<String> hashtags = List.of("#큐레이터", "#해시태그", "#입니다");
        }

        public static class host1 {

            public static class _Relation {

                public static List<Event> events() {
                    return null; // TODO: Implement
                }
            }

            public static final long id = 301;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.host);
            public static final String email = "host1@example.com";
            public static final String nickname = "nick_host_12";
            public static final String phone = "010-4545-6767";
            public static final String hashedPassword = "hashed_password";
            public static final MemberImage profileImage = ImageData.member.host.entity.get();
            public static final String profileImageUrl = ImageData.R.member.host.url;
            public static final String profileImageThumbnailUrl = ImageData.getThumbnailUrl(profileImage);
            public static final String intro = "주최자 자기소개입니다.";
            public static final List<String> hashtags = List.of("#주최자", "#해시태그", "#입니다");

        }

        public static class welcome1 {

            public static final long id = 401;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.welcome);
            public static final AccountType accountType = AccountType.kakao;
            public static final String email = "welcome2@example.com";
            public static final String nickname = "nick_normal_333";
            public static final String phone = "010-4414-1144";
            public static final MemberImage profileImage = ImageData.member.welcome.entity.get();
            public static final String profileImageUrl = ImageData.R.member.welcome.url;
            public static final String profileImageThumbnailUrl = ImageData.getThumbnailUrl(profileImage);
            public static final Area area = Area.서울;
            public static final Gender gender = Gender.MAN;
            public static final String socialId = "456456456456";

        }

        public static class admin1 {

            public static final long id = 501L;
            public static final MemberIdentifier memberIdentifier = new MemberIdentifier(id, MemberType.admin);
        }
    }

    public static class normal1 {

        public static class entity {

            public static Member get() {
                Member ret = Member.builder()
                    .accountType(R.normal1.accountType)
                    .email(R.normal1.email)
                    .nickname(R.normal1.nickname)
                    .phone(R.normal1.phone)
                    .profileImage(R.normal1.profileImage)
                    .profileImageThumbnailUrl(R.normal1.profileImageThumbnailUrl)
                    .area(R.normal1.area)
                    .gender(R.normal1.gender)
                    .socialId(R.normal1.socialId)
                    .build();
                ReflectionTestUtils.setField(ret, "id", R.normal1.id);
                ReflectionTestUtils.setField(ret, "memberType", R.normal1.memberType);
                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }

        public static class request {

        }

        public static class response {

        }
    }

    public static class curator1 {

        public static class entity {

            public static Curator get() {
                Curator ret = Curator.curatorBuilder()
                    .accountType(R.curator1.accountType)
                    .email(R.curator1.email)
                    .nickname(R.curator1.nickname)
                    .phone(R.curator1.phone)
                    .profileImage(R.curator1.profileImage)
                    .profileImageThumbnailUrl(R.curator1.profileImageThumbnailUrl)
                    .area(R.curator1.area)
                    .gender(R.curator1.gender)
                    .socialId(R.curator1.socialId)
                    .build();
                ReflectionTestUtils.setField(ret, "id", R.curator1.id);
                ReflectionTestUtils.setField(ret, "intro", R.curator1.intro);
                ReflectionTestUtils.setField(ret, "hashtags", R.curator1.hashtags);
                ReflectionTestUtils.setField(ret, "likes", R.curator1.likes);

                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }
        public static class request {

        }

        public static class response {

            public static CurationCuratorResponse getCurationCuratorResponse() {
                return CurationCuratorResponse.builder()
                    .nickname(R.curator1.nickname)
                    .email(R.curator1.email)
                    .phone(R.curator1.phone)
                    .profileImageUrl(R.curator1.profileImageUrl)
                    .intro(R.curator1.intro)
                    .likes(R.curator1.likes)
                    .hashtags(R.curator1.hashtags)
                    .build();
            }
        }
    }

    public static class host1 {

        public static class entity {

            public static Host get() {
                Host ret = Host.builder()
                    .email(R.host1.email)
                    .nickname(R.host1.nickname)
                    .phone(R.host1.phone)
                    .hashedPassword(R.host1.hashedPassword)
                    .profileImage(R.host1.profileImage)
                    .profileImageThumbnailUrl(R.host1.profileImageThumbnailUrl)
                    .build();
                ReflectionTestUtils.setField(ret, "id", R.host1.id);
                ReflectionTestUtils.setField(ret, "intro", R.host1.intro);
                ReflectionTestUtils.setField(ret, "hashtags", R.host1.hashtags);
//                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }

        public static class request {

        }

        public static class response {

        }
    }

    public static class welcome1 {

        public static class entity {

            public static Member get() {
                Member ret = Member.builder()
                    .accountType(R.welcome1.accountType)
                    .email(R.welcome1.email)
                    .nickname(R.welcome1.nickname)
                    .phone(R.welcome1.phone)
                    .profileImage(R.welcome1.profileImage)
                    .profileImageThumbnailUrl(R.welcome1.profileImageThumbnailUrl)
                    .area(R.welcome1.area)
                    .gender(R.welcome1.gender)
                    .socialId(R.welcome1.socialId)
                    .build();
                ReflectionTestUtils.setField(ret, "id", R.welcome1.id);
//                ReflectionTestUtils.setField(ret.getProfileImage(), "baseMember", ret);
                return ret;
            }
        }

        public static class request {

        }

        public static class response {

        }
    }

    public static class admin1 {

        public static class entity {

            public static final long validAdminId = 105L;
        }

        public static class request {

        }

        public static class response {

        }
    }

    public static class response {

        public static class reservation1Member1 {

            public static ReservationMemberResponse get() {
                return ReservationMemberResponse.from(normal1.entity.get());
            }
        }
        public static class review1Member1 {
            public static ReviewMemberResponse get(){
                return ReviewMemberResponse.from(normal1.entity.get());
            }
        }
        public static class curation1Curator1 {

            public static CurationCuratorResponse get() {
                return CurationCuratorResponse.from(curator1.entity.get());
            }
        }
    }
}