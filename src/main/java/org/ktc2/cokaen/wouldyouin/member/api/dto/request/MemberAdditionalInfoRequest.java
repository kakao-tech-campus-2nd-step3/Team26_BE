package org.ktc2.cokaen.wouldyouin.member.api.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;

@Getter
@RequiredArgsConstructor
public class MemberAdditionalInfoRequest {

    private String phone;
    private Area area;
    private String gender;
}
