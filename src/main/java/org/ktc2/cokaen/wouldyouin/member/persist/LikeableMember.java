package org.ktc2.cokaen.wouldyouin.member.persist;

import java.util.Arrays;
import java.util.List;

public interface LikeableMember {

    Long getId();

    String getNickname();

    String getProfileImageUrl();

    String getIntro();

    Integer getLikes();

    void setLikes(Integer likes);

    String getHashtag();

    default void increaseLikes() {
        setLikes(getLikes() + 1);
    }

    default void decreaseLikes() {
        setLikes(getLikes() - 1);
    }

    default List<String> getHashTagList() {
        return Arrays.stream(getHashtag().split("#")).toList();
    }

    static List<MemberType> getLikeableMemberTypes() {
        return List.of(MemberType.host, MemberType.curator);
    }
}
