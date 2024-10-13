package org.ktc2.cokaen.wouldyouin.member.persist;

import java.util.Arrays;
import java.util.List;

public interface LikeableMember {

    Long getId();

    String getNickname();

    String getProfileImageUrl();

    String getIntro();

    Long getFollowers();

    String getHashtag();

    default List<String> getHashTagList() {
        return Arrays.stream(getHashtag().split("#")).toList();
    }

    static List<MemberType> getLikeableMemberTypes() {
        return List.of(MemberType.host, MemberType.curator);
    }
}
