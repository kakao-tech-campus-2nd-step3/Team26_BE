package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import jakarta.annotation.PostConstruct;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthResourcesResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.stereotype.Service;

@Service
public abstract class OauthRequestService {

    private OauthRequest oauthRequestBase;

    protected abstract AccountType getAccountType();

    protected abstract OauthRequest getOauthRequestBase();

    protected abstract OauthResourcesResponse requestLoginAndAccessResources(OauthRequest oauthRequest);

    @PostConstruct
    private void init() {
        oauthRequestBase = getOauthRequestBase();
    }

    final public OauthResourcesResponse getOauthMemberResources(String code) {
        return requestLoginAndAccessResources(oauthRequestBase.toBuilder()
            .code(code)
            .build());
    }
}
