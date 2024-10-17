package org.ktc2.cokaen.wouldyouin.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoPayResponse {

    @JsonProperty("tid")
    private final String tid;
    @JsonProperty("next_redirect_app_url")
    private final String nextRedirectAppUrl;
    @JsonProperty("next_redirect_mobile_url")
    private final String nextRedirectMobileUrl;
    @JsonProperty("next_redirect_pc_url")
    private final String nextRedirectPcUrl;
    @JsonProperty("android_app_scheme")
    private final String androidAppScheme;
    @JsonProperty("ios_app_scheme")
    private final String iosAppScheme;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
}