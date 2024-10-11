package org.ktc2.cokaen.wouldyouin.member.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface EntityGettable<T> {

    @Transactional(readOnly = true)
    T getByIdOrThrow(Long id) throws RuntimeException; // TODO: 커스텀 예외 필요;
}
