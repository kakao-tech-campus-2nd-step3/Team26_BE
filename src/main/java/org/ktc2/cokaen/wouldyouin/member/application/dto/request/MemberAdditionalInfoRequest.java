package org.ktc2.cokaen.wouldyouin.member.application.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;

@Getter
@RequiredArgsConstructor
public class MemberAdditionalInfoRequest {

    private final String phone;
    private final Area area;
    private final String gender;
}
