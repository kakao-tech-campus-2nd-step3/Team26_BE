package org.ktc2.cokaen.wouldyouin._common.api;

import org.springframework.transaction.annotation.Transactional;

// Long 기반 id로 Entity를 얻을 수 있는 인터페이스 제공
public interface EntityGettable<K, E> {

    @Transactional(readOnly = true)
    E getByIdOrThrow(K id) throws RuntimeException; // TODO: 커스텀 예외 필요;
}