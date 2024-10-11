package org.ktc2.cokaen.wouldyouin.member.application;

import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.springframework.transaction.annotation.Transactional;

public interface MemberServiceCommonBehavior {

    @Transactional
    void deleteById(Long id);

    @Transactional(readOnly = true)
    MemberResponse getMemberResponseById(Long id);
}
