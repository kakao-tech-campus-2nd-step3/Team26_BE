package org.ktc2.cokaen.wouldyouin._global.testdata;

import static org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.createValidCurator;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._global.TestData;
import org.ktc2.cokaen.wouldyouin._global.TestData.EventDomain;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.CurationCuratorResponse;
import org.springframework.test.util.ReflectionTestUtils;

public class CurationData {

    public static CurationCardRequest createValidCurationCardRequest1() {
        return CurationCardRequest.builder()
            .subtitle("큐레이션 카드 부제목1")
            .content("큐레이션 카드 내용1 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.")
            .imageIds(List.of(1301L))
            .build();
    }

    public static CurationCardRequest createValidCurationCardRequest2() {
        return CurationCardRequest.builder()
            .subtitle("큐레이션 카드 부제목2")
            .content("큐레이션 카드 내용2 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.")
            .imageIds(List.of(1302L))
            .build();
    }

    public static CurationCard createValidCurationCard1() {
        CurationCard validCurationCard1 =
            createValidCurationCardRequest1().toEntity(List.of(ImageData.createValidCurationImage1()));
        ReflectionTestUtils.setField(validCurationCard1, "id", 351L);
//        ReflectionTestUtils.setField(validCurationCard1, "curation", createValidCuration());
        return validCurationCard1;
    }

    public static CurationCard createValidCurationCard2() {
        CurationCard validCurationCard2 =
            createValidCurationCardRequest2().toEntity(List.of(ImageData.createValidCurationImage2()));
        ReflectionTestUtils.setField(validCurationCard2, "id", 352L);
//        ReflectionTestUtils.setField(validCurationCard1, "curation", createValidCuration());
        return validCurationCard2;
    }

    public static CurationCardResponse createValidCurationCardResponse1() {
        return CurationCardResponse.from(createValidCurationCard1());
    }

    public static CurationCardResponse createValidCurationCardResponse2() {
        return CurationCardResponse.from(createValidCurationCard2());
    }

    public static CurationCreateRequest createValidCurationCreateRequest() {
        return CurationCreateRequest.builder()
            .title("큐레이션 제목")
            .content("큐레이션 본문")
            .curationCards(List.of(createValidCurationCardRequest1()))
            .area(Area.전체)
            .hashtags(List.of("#큐레이션", "#해시태그"))
            .eventIds(List.of(201L))
            .build();
    }

    public static CurationEditRequest createValidCurationEditRequest() {
        return CurationEditRequest.builder()
            .title("큐레이션 제목 수정")
            .content("큐레이션 본문 수정")
            .curationCards(List.of(createValidCurationCardRequest2()))
            .area(Area.광주)
            .hashtags(List.of("수정 해시태그"))
            .eventIds(List.of(202L))
            .build();
    }

    public static Curation createValidCuration() {
        Curation validCuration = createValidCurationCreateRequest().toEntity(
            createValidCurator(), List.of(createValidCurationCard1()), List.of(EventDomain.createValidEvent()));
        ReflectionTestUtils.setField(validCuration, "id", 301L);
        ReflectionTestUtils.setField(validCuration, "createdDate", LocalDateTime.of(2023, 3, 23, 0, 0));
        ReflectionTestUtils.setField(validCuration, "modifiedDate", LocalDateTime.of(2024, 3, 23, 0, 0));
        return validCuration;
    }

    public static CurationCuratorResponse createValidCurationCuratorResponse() {
        return CurationCuratorResponse.from(createValidCurator());
    }

    public static CurationResponse createValidCurationResponse() {
        return CurationResponse.from(createValidCuration());
    }

    public static CurationSliceResponse createValidCurationSliceResponse() {
        return CurationSliceResponse.builder()
            .curations(List.of(createValidCurationResponse()))
            .sliceInfo(TestData.createSliceInfo())
            .build();
    }
}