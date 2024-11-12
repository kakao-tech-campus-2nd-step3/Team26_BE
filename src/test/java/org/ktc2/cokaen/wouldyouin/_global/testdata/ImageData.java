package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.R.memberImage;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

public class ImageData {

    public static class R {
        public static class memberImage {
            public static final Long id = 1101L;
            public static final String name = "memberImage1.jpg";
            public static final String url = "https://wouldyouin.store/api/images/memberImage1.jpg";
            public static final Long size = 10L;
            public static final String extension = ".jpg";
        }
    }

    // name: v파일 이름만 있어야 함
        // 예: memberImage1.png
    // url: 도메인 그거
        // 예: https://localhost:8080/images/member/Image1.png

    public static class member {
        public static class normal {
            public static class entity {
                public static MemberImage create() {
                    MemberImage validMemberImage = MemberImage.builder()
                        .name(memberImage.name)
                        .size(memberImage.size)
                        .extension(memberImage.extension)
                        .build();
                    ReflectionTestUtils.setField(validMemberImage, "id", memberImage.id);
            //        ReflectionTestUtils.setField(validMemberImage, "baseMember", createValidMember());
                    return validMemberImage;
                }
            }
            public static class request {
                public static ImageRequest create() {
                    return ImageRequest.builder()
                        .url(memberImage.url)
                        .size(memberImage.size)
                        .extension(memberImage.extension)
                        .build();
                }
            }
        }
        public static class curator {
            public static class entity {

            }
            public static class request {

            }
        }
        public static class host {
            public static class entity {

            }
            public static class request {

            }
        }
    }

    public static class event {
        public static class entity {

            public static EventImage createValidEventImage() {
                EventImage validEventImage = EventImage.builder()
                    .name("wouldyouin.com/eventImage1.jpg")
                    .size(20L)
                    .extension(".jpg")
                    .build();
                ReflectionTestUtils.setField(validEventImage, "id", 1201L);
        //        ReflectionTestUtils.setField(validEventImage, "baseMember", createValidHost());
                return validEventImage;
            }
        }
        public static class request {

            public static ImageRequest createValidEventImageRequest() {
                return ImageRequest.builder()
                    .url("wouldyouin.com/eventImage1.jpg")
                    .size(20L)
                    .extension(".jpg")
                    .build();
            }
        }
    }

    public static class curation1 {
        public static class entity {

            public static CurationImage createValidCurationImage1() {
                CurationImage validCurationImage = CurationImage.builder()
                    .name("wouldyouin.com/curationImage1.jpg")
                    .size(30L)
                    .extension(".jpg")
                    .build();
                ReflectionTestUtils.setField(validCurationImage, "id", 1301L);
        //        ReflectionTestUtils.setField(validCurationImage, "baseMember", createValidCurator());
                return validCurationImage;
            }
        }
        public static class request {

            public static ImageRequest createValidCurationImageRequest() {
                return ImageRequest.builder()
                    .url("wouldyouin.com/curationImage1.jpg")
                    .size(30L)
                    .extension(".jpg")
                    .build();
            }
        }
        public static class response {

            public static ImageResponse createValidCurationImageResponse1() {
                return ImageResponse.builder()
                    .id(1301L)
                    .url("wouldyouin.com/curationImage1.jpg")
                    .size(30L)
                    .extension(".jpg")
                    .createdDate(LocalDateTime.of(2024, 3, 23, 0, 0))
                    .build();
            }
        }
    }

    public static class curation2 {
        public static class entity {

            public static CurationImage createValidCurationImage2() {
                CurationImage validCurationImage = CurationImage.builder()
                    .name("wouldyouin.com/curationImage2.jpg")
                    .size(30L)
                    .extension(".jpg")
                    .build();
                ReflectionTestUtils.setField(validCurationImage, "id", 1302L);
        //        ReflectionTestUtils.setField(validCurationImage, "baseMember", createValidCurator());
                return validCurationImage;
            }
        }
        public static class request {

        }
        public static class response {

            public static ImageResponse createValidCurationImageResponse2() {
                return ImageResponse.builder()
                    .id(1302L)
                    .url("wouldyouin.com/curationImage2.jpg")
                    .size(30L)
                    .extension(".png")
                    .createdDate(LocalDateTime.of(2024, 3, 23, 0, 0))
                    .build();
            }
        }
    }

    public static class advertisement {
        public static class entity {

            public static AdvertisementImage createValidAdImage() {
                AdvertisementImage validAdImage = AdvertisementImage.builder()
                    .name("adImage1.jpg")
                    .size(40L)
                    .extension(".jpg")
                    .build();
                ReflectionTestUtils.setField(validAdImage, "id", 1401L);
        //        ReflectionTestUtils.setField(validAdImage, "baseMember", createValidMember());
                return validAdImage;
            }
        }
        public static class request {

            public static ImageRequest createValidAdImageRequest() {
                return ImageRequest.builder()
                    .url("wouldyouin.com/adImage1.jpg")
                    .size(40L)
                    .extension(".jpg")
                    .build();
            }
        }
    }

    public static MockMultipartFile createValidMultipartFile1() {
        return new MockMultipartFile("images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "imageData1".getBytes());
    }

    public static MockMultipartFile createValidMultipartFile2() {
        return new MockMultipartFile("images", "image2.png", MediaType.IMAGE_JPEG_VALUE, "imageData2".getBytes());
    }
}