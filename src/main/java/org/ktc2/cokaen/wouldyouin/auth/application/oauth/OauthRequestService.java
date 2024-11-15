package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import jakarta.annotation.PostConstruct;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthResourcesResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.stereotype.Service;

@Service
public abstract class OauthRequestService {

    protected abstract AccountType getAccountType();

    protected abstract OauthResourcesResponse requestLoginAndAccessResources(OauthRequest oauthRequest);

    public abstract OauthResourcesResponse getOauthMemberResources(String code);
}
