package org.ktc2.cokaen.wouldyouin.Image.api;

import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;

public enum ImageDomain {
    MEMBER("/images/member/", MemberImage.class),
    CURATION("/images/curation/", CurationImage.class),
    EVENT("/images/event/", EventImage.class);

    @Getter
    private final String subPath;
    private final Class<? extends Image> classType;

    ImageDomain(String subPath, Class<? extends Image> classType) {
        this.subPath = subPath;
        this.classType = classType;
    }

    public <T extends Image> Class<T> getClassType() {
        return (Class<T>) this.classType;
    }
}