package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.stereotype.Component;

@Component
public class OauthRequestServiceFactory {

    private final Map<AccountType, OauthRequestService> map;

    public OauthRequestServiceFactory(List<OauthRequestService> list) {
        this.map = list.stream()
            .collect(Collectors.toConcurrentMap(
                OauthRequestService::getAccountType,
                Function.identity()));
    }

    public OauthRequestService getServiceFrom(AccountType accountType) {
        return this.map.get(accountType);
    }
}
