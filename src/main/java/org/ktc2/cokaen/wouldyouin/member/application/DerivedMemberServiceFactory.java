package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DerivedMemberServiceFactory {

    private final Map<MemberType, MemberServiceCommonBehavior> map;

    public DerivedMemberServiceFactory(@Autowired List<MemberServiceCommonBehavior> commonBehavior) {
        map = commonBehavior.stream()
            .collect(Collectors.toConcurrentMap(
                MemberServiceCommonBehavior::getTargetMemberType,
                Function.identity()));
    }

    public MemberServiceCommonBehavior get(MemberType memberType) {
        return map.get(memberType);
    }
}
