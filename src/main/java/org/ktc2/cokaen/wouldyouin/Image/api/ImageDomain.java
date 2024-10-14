package org.ktc2.cokaen.wouldyouin.Image.api;

import lombok.Getter;

@Getter
public enum ImageDomain {
    MEMBER("/images/member/"),
    CURATION("/images/curation/"),
    EVENT("/images/event/");

    private final String subPath;

    ImageDomain(String subPath) {
        this.subPath = subPath;
    }
}