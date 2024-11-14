package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.like.api.dto.LikeResponse;
import org.ktc2.cokaen.wouldyouin.like.api.dto.LikeSliceResponse;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLike;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLike;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

public class LikeData {

    public static class R {
        public static class likeHost1 {
            public static class byNormal1 {
                public static class _Relation {
                    public static Host targetMember() {
                        return MemberData.host1.entity.get();
                    }
                    public static Member member() {
                        return MemberData.normal1.entity.get();
                    }
                }
                public static class response {
                    public static final Long id = MemberData.R.host1.id;
                    public static final String nickname = MemberData.R.host1.nickname;
                    public static final String intro = MemberData.R.host1.intro;
                    public static final List<String> hashtags = MemberData.R.host1.hashtags;
                    public static final String profileImageUrl = MemberData.R.host1.profileImageUrl;
                }
            }
            public static class byCurator1 {
                public static class _Relation {
                    public static Host targetMember() {
                        return MemberData.host1.entity.get();
                    }
                    public static Curator member() {
                        return MemberData.curator1.entity.get();
                    }
                }
                public static class response {
                    public static final Long id = MemberData.R.host1.id;
                    public static final String nickname = MemberData.R.host1.nickname;
                    public static final String intro = MemberData.R.host1.intro;
                    public static final List<String> hashtags = MemberData.R.host1.hashtags;
                    public static final String profileImageUrl = MemberData.R.host1.profileImageUrl;
                }
            }
        }
        public static class likeCurator1 {
            public static class byNormal1 {
                public static class _Relation {
                    public static Curator targetMember() {
                        return MemberData.curator1.entity.get();
                    }
                    public static Member member() {
                        return MemberData.normal1.entity.get();
                    }
                }
                public static class response {
                    public static final Long id = MemberData.R.curator1.id;
                    public static final String nickname = MemberData.R.curator1.nickname;
                    public static final String intro = MemberData.R.curator1.intro;
                    public static final List<String> hashtags = MemberData.R.curator1.hashtags;
                    public static final String profileImageUrl = MemberData.R.curator1.profileImageUrl;
                }
            }
        }
    }

    public static class likeHost1 {
        public static class byNormal1 {
            public static class entity {
                public static HostLike get() {
                    return HostLike.builder()
                        .targetMember(R.likeHost1.byNormal1._Relation.targetMember())
                        .member(R.likeHost1.byNormal1._Relation.member())
                        .build();
                }
            }
            public static class response {
                public static LikeResponse get() {
                    return LikeResponse.builder()
                        .memberId(R.likeHost1.byNormal1.response.id)
                        .nickname(R.likeHost1.byNormal1.response.nickname)
                        .intro(R.likeHost1.byNormal1.response.intro)
                        .hashtags(R.likeHost1.byNormal1.response.hashtags)
                        .profileImageUrl(R.likeHost1.byNormal1.response.profileImageUrl)
                        .build();
                }
            }
        }
        public static class byCurator1 {
            public static class entity {
                public static HostLike get() {
                    return HostLike.builder()
                        .targetMember(R.likeHost1.byCurator1._Relation.targetMember())
                        .member(R.likeHost1.byCurator1._Relation.member())
                        .build();
                }
            }
            public static class response {
                public static LikeResponse get() {
                    return LikeResponse.builder()
                        .memberId(R.likeHost1.byCurator1.response.id)
                        .nickname(R.likeHost1.byCurator1.response.nickname)
                        .intro(R.likeHost1.byCurator1.response.intro)
                        .hashtags(R.likeHost1.byCurator1.response.hashtags)
                        .profileImageUrl(R.likeHost1.byCurator1.response.profileImageUrl)
                        .build();
                }
            }
        }
    }

    public static class likeCurator1 {
        public static class byNormal1 {
            public static class entity {
                public static CuratorLike get() {
                    return CuratorLike.builder()
                        .targetMember(R.likeCurator1.byNormal1._Relation.targetMember())
                        .member(R.likeCurator1.byNormal1._Relation.member())
                        .build();
                }
            }
            public static class response {
                public static LikeResponse get() {
                    return LikeResponse.builder()
                        .memberId(R.likeCurator1.byNormal1.response.id)
                        .nickname(R.likeCurator1.byNormal1.response.nickname)
                        .intro(R.likeCurator1.byNormal1.response.intro)
                        .hashtags(R.likeCurator1.byNormal1.response.hashtags)
                        .profileImageUrl(R.likeCurator1.byNormal1.response.profileImageUrl)
                        .build();
                }
            }
        }
    }

    public static class normal1LikeSliceResponse {
        public static LikeSliceResponse get() {
            return LikeSliceResponse.builder()
                .likes(List.of(
                    LikeResponse.builder()
                        .memberId(R.likeHost1.byNormal1.response.id)
                        .nickname(R.likeHost1.byNormal1.response.nickname)
                        .intro(R.likeHost1.byNormal1.response.intro)
                        .hashtags(R.likeHost1.byNormal1.response.hashtags)
                        .profileImageUrl(R.likeHost1.byNormal1.response.profileImageUrl)
                        .build(),
                    LikeResponse.builder()
                        .memberId(R.likeCurator1.byNormal1.response.id)
                        .nickname(R.likeCurator1.byNormal1.response.nickname)
                        .intro(R.likeCurator1.byNormal1.response.intro)
                        .hashtags(R.likeCurator1.byNormal1.response.hashtags)
                        .profileImageUrl(R.likeCurator1.byNormal1.response.profileImageUrl)
                        .build()
                ))
                .sliceInfo(CommonData.sliceInfo.like.get())
                .build();
        }
    }
}
