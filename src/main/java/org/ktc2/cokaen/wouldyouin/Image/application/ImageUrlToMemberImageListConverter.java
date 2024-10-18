package org.ktc2.cokaen.wouldyouin.Image.application;


import java.util.List;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;

public interface ImageUrlToMemberImageListConverter {
    List<MemberImage> convert(String imageUrl);
}
