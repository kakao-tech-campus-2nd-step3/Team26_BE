package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

public class ImageData {

    public static final long validMemberImageId = 1101L;
    public static final long validEventImageId = 1201L;
    public static final long validCurationImageId = 1301L;
    public static final long validAdImageId = 1401L;

    public static MemberImage createValidMemberImage() {
        MemberImage validMemberImage = MemberImage.builder()
            .url("member/memberImage.jpg")
            .size(10L)
            .extension(".jpg")
            .build();
        ReflectionTestUtils.setField(validMemberImage, "id", 1101L);
//            ReflectionTestUtils.setField(validMemberImage, "baseMember", MemberDomain.createValidMember());
        return validMemberImage;
    }

    public static EventImage createValidEventImage() {
        EventImage validEventImage = EventImage.builder()
            .url("event/eventImage.jpg")
            .size(20L)
            .extension(".jpg")
            .build();
        ReflectionTestUtils.setField(validEventImage, "id", 1201L);
//            ReflectionTestUtils.setField(validMemberImage, "baseMember", MemberDomain.createValidMember());
        return validEventImage;
    }

    public static CurationImage createValidCurationImage1() {
        CurationImage validCurationImage = CurationImage.builder()
            .url("curation/curationImage1.jpg")
            .size(30L)
            .extension(".jpg")
            .build();
        ReflectionTestUtils.setField(validCurationImage, "id", 1301L);
//            ReflectionTestUtils.setField(validMemberImage, "baseMember", MemberDomain.createValidMember());
        return validCurationImage;
    }

    public static CurationImage createValidCurationImage2() {
        CurationImage validCurationImage = CurationImage.builder()
            .url("curation/curationImage2.jpg")
            .size(30L)
            .extension(".jpg")
            .build();
        ReflectionTestUtils.setField(validCurationImage, "id", 1302L);
//            ReflectionTestUtils.setField(validMemberImage, "baseMember", MemberDomain.createValidMember());
        return validCurationImage;
    }

    public static AdvertisementImage createValidAdImage() {
        AdvertisementImage validAdImage = AdvertisementImage.builder()
            .url("curation/curationImage.jpg")
            .size(40L)
            .extension(".jpg")
            .build();
        ReflectionTestUtils.setField(validAdImage, "id", 1401L);
//            ReflectionTestUtils.setField(validMemberImage, "baseMember", MemberDomain.createValidMember());
        return validAdImage;
    }

    public static ImageRequest createValidMemberImageRequest() {
        return ImageRequest.builder()
            .url("member/memberImage.jpg")
            .size(10L)
            .extension(".jpg")
            .build();
    }

    public static ImageRequest createValidEventImageRequest() {
        return ImageRequest.builder()
            .url("event/eventImage.jpg")
            .size(20L)
            .extension(".jpg")
            .build();
    }

    public static ImageRequest createValidCurationImageRequest() {
        return ImageRequest.builder()
            .url("curation/curationImage.jpg")
            .size(30L)
            .extension(".jpg")
            .build();
    }

    public static ImageRequest createValidAdImageRequest() {
        return ImageRequest.builder()
            .url("ad/adImage.jpg")
            .size(40L)
            .extension(".jpg")
            .build();
    }

    public static ImageResponse createValidImageResponse1() {
        return ImageResponse.builder()
            .id(1L)
            .url("curationImageUrl.jpg")
            .size(10L)
            .extension(".jpg")
            .createdDate(LocalDateTime.of(2024, 3, 23, 0, 0))
            .build();
    }

    public static ImageResponse createValidImageResponse2() {
        return ImageResponse.builder()
            .id(2L)
            .url("memberImage.png")
            .size(20L)
            .extension(".png")
            .createdDate(LocalDateTime.of(2024, 3, 23, 0, 0))
            .build();
    }

    public static MockMultipartFile createValidMultipartFile1() {
        return new MockMultipartFile("images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "imageData1".getBytes());
    }

    public static MockMultipartFile createValidMultipartFile2() {
        return new MockMultipartFile("images", "image2.png", MediaType.IMAGE_JPEG_VALUE, "imageData2".getBytes());
    }
}
