package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

public class ImageData {

    // name: 파일 이름만 있어야 함 => entity, request
    // 예: memberImage1.png
    // url: 도메인 그거 => response
    // 예: https://localhost:8080/images/member/Image1.png

    public static class R {
        public static class member {
            public static class normal {
                public static final Long id = 1101L;
                public static final String name = "memberImage1.jpg";
                public static final String url = "https://wouldyouin.store/api/images/" + name;
                public static final Long size = 11000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
            public static class curator {
                public static final Long id = 1102L;
                public static final String name = "curatorImage1.jpg";
                public static final String url = "https://wouldyouin.store/api/images/" + name;
                public static final Long size = 12000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
            public static class host {
                public static final Long id = 1103L;
                public static final String name = "hostImage1.jpg";
                public static final String url = "https://wouldyouin.store/api/images/" + name;
                public static final Long size = 13000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
            public static class welcome {
                public static final Long id = 1104L;
                public static final String name = "welcomeImage1.jpg";
                public static final String url = "https://wouldyouin.store/api/images/" + name;
                public static final Long size = 14000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
        }
        public static class event {
            public static final Long id = 1201L;
            public static final String name = "eventImage1.jpg";
            public static final String url = "https://wouldyouin.store/api/images/" + name;
            public static final Long size = 22000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation1 {
            public static final Long id = 1301L;
            public static final String name = "curationImage1.jpg";
            public static final String url = "https://wouldyouin.store/api/images/" + name;
            public static final Long size = 31000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation2 {
            public static final Long id = 1302L;
            public static final String name = "curationImage2.jpg";
            public static final String url = "https://wouldyouin.store/api/images/" + name;
            public static final Long size = 32000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class advertisement {
            public static final Long id = 1401L;
            public static final String name = "adImage1.jpg";
            public static final String url = "https://wouldyouin.store/api/images/" + name;
            public static final Long size = 40000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class mockMultipartFile1 {
            public static final String name = "images";
            public static final String originalFileName = "image1.jpg";
            public static final String contentType = MediaType.IMAGE_JPEG_VALUE;
            public static final byte[] content = "imageData1".getBytes();
        }
        public static class mockMultipartFile2 {
            public static final String name = "images";
            public static final String originalFileName = "image2.png";
            public static final String contentType = MediaType.IMAGE_JPEG_VALUE;
            public static final byte[] content = "imageData2".getBytes();
        }
    }

    private static void setImageFields(Image image, Long id, LocalDateTime createdDate) {
        ReflectionTestUtils.setField(image, "id", id);
        ReflectionTestUtils.setField(image, "createdDate", createdDate);
    }

    public static class member {
        public static class normal {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.normal.name)
                        .size(R.member.normal.size)
                        .extension(R.member.normal.extension)
                        .build();
                    setImageFields(ret, R.member.normal.id, R.member.normal.createdDate);
            //        ReflectionTestUtils.setField(ret, "baseMember", createValidMember());
                    return ret;
                }
            }
            public static class request {
                public static ImageRequest get() {
                    return ImageRequest.builder()
                        .name(R.member.normal.url)
                        .size(R.member.normal.size)
                        .extension(R.member.normal.extension)
                        .build();
                }
            }
            public static class response {

            }
        }
        public static class curator {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.curator.name)
                        .size(R.member.curator.size)
                        .extension(R.member.curator.extension)
                        .build();
                    setImageFields(ret, R.member.curator.id, R.member.curator.createdDate);
                    // ReflectionTestUtils.setField(ret, "baseMember", createValidCurator());
                    return ret;
                }
            }
            public static class request {

            }
            public static class response {

            }
        }
        public static class host {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.host.name)
                        .size(R.member.host.size)
                        .extension(R.member.host.extension)
                        .build();
                    setImageFields(ret, R.member.host.id, R.member.host.createdDate);
                    // ReflectionTestUtils.setField(ret, "baseMember", createValidHost());
                    return ret;
                }
            }
            public static class request {

            }
            public static class response {

            }
        }
        public static class welcome {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.welcome.name)
                        .size(R.member.welcome.size)
                        .extension(R.member.welcome.extension)
                        .build();
                    setImageFields(ret, R.member.welcome.id, R.member.welcome.createdDate);
                    // ReflectionTestUtils.setField(validMemberImage, "baseMember", createValidWelcome());
                    return ret;
                }
            }
            public static class request {

            }
            public static class response {

            }
        }
    }

    public static class event {
        public static class entity {
            public static EventImage get() {
                EventImage ret = EventImage.builder()
                    .name(R.event.name)
                    .size(R.event.size)
                    .extension(R.event.extension)
                    .build();
                setImageFields(ret, R.event.id, R.event.createdDate);
//                ReflectionTestUtils.setField(ret, "event", createValidEvent());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.event.name)
                    .size(R.event.size)
                    .extension(R.event.extension)
                    .build();
            }
        }
        public static class response {

        }
    }

    public static class curation1 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation1.name)
                    .size(R.curation1.size)
                    .extension(R.curation1.extension)
                    .build();
                setImageFields(ret, R.curation1.id, R.curation1.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation1.name)
                    .size(R.curation1.size)
                    .extension(R.curation1.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation1.id)
                    .url(R.curation1.url)
                    .size(R.curation1.size)
                    .extension(R.curation1.extension)
                    .createdDate(R.curation1.createdDate)
                    .build();
            }
        }
    }

    public static class curation2 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation2.name)
                    .size(R.curation2.size)
                    .extension(R.curation2.extension)
                    .build();
                setImageFields(ret, R.curation2.id, R.curation2.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {

        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation2.id)
                    .url(R.curation2.url)
                    .size(R.curation2.size)
                    .extension(R.curation2.extension)
                    .createdDate(R.curation2.createdDate)
                    .build();
            }
        }
    }

    public static class advertisement {
        public static class entity {
            public static AdvertisementImage get() {
                AdvertisementImage ret = AdvertisementImage.builder()
                    .name(R.advertisement.name)
                    .size(R.advertisement.size)
                    .extension(R.advertisement.extension)
                    .build();
                setImageFields(ret, R.advertisement.id, R.advertisement.createdDate);
                // ReflectionTestUtils.setField(ret, "advertisement", createValidMember());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.advertisement.name)
                    .size(R.advertisement.size)
                    .extension(R.advertisement.extension)
                    .build();
            }
        }
    }

    public static class mockMultipartFile1 {
        public static MockMultipartFile get() {
            return new MockMultipartFile(
                R.mockMultipartFile1.name,
                R.mockMultipartFile1.originalFileName,
                R.mockMultipartFile1.contentType,
                R.mockMultipartFile1.content);
        }
    }

    public static class mockMultipartFile2 {
        public static MockMultipartFile get() {
            return new MockMultipartFile(
                R.mockMultipartFile2.name,
                R.mockMultipartFile2.originalFileName,
                R.mockMultipartFile2.contentType,
                R.mockMultipartFile2.content);
        }
    }

}