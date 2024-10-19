package org.ktc2.cokaen.wouldyouin.member.persist;

import lombok.Getter;

@Getter
public enum MemberType {
    welcome, // 처음 가입해 추가정보가 필요한 멤버
    normal,
    curator,
    host,
    admin;

}