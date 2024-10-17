package org.ktc2.cokaen.wouldyouin.payment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoPayResponse {

    private final String tid;
    private final String next_redirect_app_url;
    private final String next_redirect_mobile_url;
    private final String next_redirect_pc_url;
    private final String android_app_scheme;
    private final String ios_app_scheme;
    private final LocalDateTime created_at;
}