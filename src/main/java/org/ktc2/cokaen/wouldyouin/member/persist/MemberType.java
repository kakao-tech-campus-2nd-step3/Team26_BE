package org.ktc2.cokaen.wouldyouin.member.persist;

import lombok.Getter;

@Getter
public enum MemberType {
    normal("memberService"),
    curator("curatorService"),
    host("hostService"),
    any(""),
    admin("");

    private final String serviceName;

    MemberType(String serviceName) {
        this.serviceName = serviceName;
    }

}

